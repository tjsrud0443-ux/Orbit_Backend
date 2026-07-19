package com.study.app.domains.holiday;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HolidayService {

	@Autowired
    private HolidayDAO holidayDAO;

    @Value("${holiday.api-key}")
    private String apiKey;
    
    // 자바에서 다른 서버(공공데이터포털)로 HTTP 요청을 보낼 때 쓰는 도구. 프론트의 axios와 같은 역할
    private final RestTemplate restTemplate = new RestTemplate();

    // 공공데이터포털이 응답으로 준 JSON 문자열을 자바가 다룰 수 있는 구조로 변환해주는 도구
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 컨트롤러가 호출하는 진입점.
     * 1) DB에 이미 있으면 API 호출 없이 바로 반환
     * 2) 없으면 외부 API 호출 → DB 저장 → 반환
     */
    public List<HolidayDTO> getHolidays(int year) {
        // 해당 연도가 DB에 저장되어있나?
        if (holidayDAO.countByYear(year) > 0) {
            // 이미 있으면 외부 API 호출 없이 DB에서 바로 꺼내서 반환 (캐시 히트)
            return holidayDAO.getByYear(year);
        }

        // DB에 없으면(캐시 미스) 공공데이터포털에 실제로 요청
        List<HolidayDTO> fetched = fetchFromExternalApi(year);

        // 받아온 데이터를 한 건씩 DB에 저장 → 다음부터는 이 연도는 DB에서 바로 응답 가능
        for (HolidayDTO dto : fetched) {
            holidayDAO.insertHoliday(year, dto);
        }

        // 방금 API에서 받아온 데이터를 그대로 프론트로 반환
        return fetched;
    }
    
    /*
     * 특정 연도를 강제로 재동기화
     * 기존 DB 데이터를 삭제하고 외부 API에서 다시 받아와 저장
     * 관리자 전용 기능 (정부의 대체공휴일 추가 발표, 로직 개선 등에 대응)
     */
    public List<HolidayDTO> resyncHolidays(int year) {
        holidayDAO.deleteByYear(year);

        List<HolidayDTO> fetched = fetchFromExternalApi(year);

        for (HolidayDTO dto : fetched) {
            try {
                holidayDAO.insertHoliday(year, dto);
            } catch (Exception e) {
                // 유니크 제약 위반 시 로그만 남기고 계속 진행
                System.out.println("중복 공휴일 insert 스킵: " + dto.getDate_name());
            }
        }

        return fetched;
    }
    
    /**
     * 공공데이터포털에 실제로 요청을 보내고, 응답 JSON을 파싱해서
     * HolidayDTO 리스트로 변환하는 내부 함수
     */
    private List<HolidayDTO> fetchFromExternalApi(int year) {
        List<HolidayDTO> result = new ArrayList<>();

        // 공휴일(쉬는 날) 조회 → is_holiday = 'Y'
        List<HolidayDTO> restDays = fetchFromEndpoint(year, "getRestDeInfo");
        for (HolidayDTO dto : restDays) {
            dto.setIs_holiday("Y");
        }
        result.addAll(restDays);
        
        // 국경일 조회 → is_holiday = 'N' (제헌절처럼 안 쉬는 국경일 포함)
        List<HolidayDTO> nationalDays = fetchFromEndpoint(year, "getHoliDeInfo");
        for (HolidayDTO dto : nationalDays) {
            // 이미 같은 날짜+이름으로 들어온 경우(삼일절, 광복절 등 쉬는 국경일) 중복 방지
            boolean alreadyExists = result.stream().anyMatch(existing ->
                    existing.getLocdate().equals(dto.getLocdate()) &&
                    existing.getDate_name().equals(dto.getDate_name())
            );
            if (!alreadyExists) {
                dto.setIs_holiday("N");
                result.add(dto);
            }
        }       

        return result;
    }
    
    /**
     * 공공데이터포털의 특정 엔드포인트(endpoint)를 호출해서 HolidayDTO 리스트로 변환
     * getRestDeInfo, getHoliDeInfo 둘 다 응답 구조가 동일해서 이 함수 하나로 공용 처리
     */
    private List<HolidayDTO> fetchFromEndpoint(int year, String endpoint) {
        List<HolidayDTO> result = new ArrayList<>();

        String url = "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/" + endpoint
                + "?serviceKey=" + apiKey
                + "&solYear=" + year
                + "&numOfRows=100"
                + "&_type=json";

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            JsonNode header = root.path("response").path("header");
            if (!"00".equals(header.path("resultCode").asText())) {
                System.out.println("공휴일 API 응답 에러(" + endpoint + "): " + header.path("resultMsg").asText());
                return result;
            }

            JsonNode items = root.path("response").path("body").path("items").path("item");

            if (items.isArray()) {
                for (JsonNode item : items) {
                    result.add(toDTO(item));
                }
            } else if (!items.isMissingNode() && !items.isNull()) {
                result.add(toDTO(items));
            }

        } catch (Exception e) {
            System.out.println("공휴일 API 호출 실패 (" + endpoint + ", " + year + "): " + e.getMessage());
        }

        return result;
    }
    
    /**
     * API 응답의 개별 항목(JsonNode)을 우리 프로젝트의 HolidayDTO로 변환
     */
    private HolidayDTO toDTO(JsonNode item) {
        // API의 locdate는 20220101 같은 숫자 형태로 옴
        String locdate = String.valueOf(item.path("locdate").asLong());

        // 'YYYY-MM-DD' 문자열로 변환 (mapper의 TO_TIMESTAMP에서 이 형식을 기대함)
        String formatted = locdate.substring(0, 4) + "-" + locdate.substring(4, 6) + "-" + locdate.substring(6, 8);

        HolidayDTO dto = new HolidayDTO();
        dto.setLocdate(formatted);
        dto.setDate_name(item.path("dateName").asText());       // 공휴일명 (예: "신정")
        dto.setIs_holiday(item.path("isHoliday").asText("Y"));  // 없으면 기본값 "Y"
        return dto;
    }
}
