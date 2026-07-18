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
    
    /**
     * 공공데이터포털에 실제로 요청을 보내고, 응답 JSON을 파싱해서
     * HolidayDTO 리스트로 변환하는 내부 함수
     */
    private List<HolidayDTO> fetchFromExternalApi(int year) {
        List<HolidayDTO> result = new ArrayList<>();

        // 공공데이터포털 요청 URL 조립 (year를 제외하면 매번 동일한 형식)
        String url = "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"
                + "?serviceKey=" + apiKey
                + "&solYear=" + year
                + "&numOfRows=100"   // 1년치 공휴일이 다 담기도록 넉넉하게 설정
                + "&_type=json";     // 응답 형식을 JSON으로 요청 (기본은 XML)

        try {
            // GET 요청을 보내고 응답 body를 그대로 문자열로 받음 (아직 JSON 파싱 전)
            String response = restTemplate.getForObject(url, String.class);

            // 문자열을 JSON 트리 구조로 파싱 → 이제 .path()로 값에 접근 가능
            JsonNode root = objectMapper.readTree(response);

            // 응답 안에 header가 있는지 확인 (resultCode로 API 자체의 성공/실패 여부 판단)
            JsonNode header = root.path("response").path("header");
            if (!"00".equals(header.path("resultCode").asText())) {
                // "00"이 아니면 API 레벨 에러(트래픽 초과, 서비스키 오류 등)
                // 여기서 빈 리스트를 반환하면 getHolidays()에서 insertHoliday가 실행 안 됨
                // → DB에 아무것도 안 남음 → 다음 요청 때 countByYear가 여전히 0 → 자동 재시도됨
                System.out.println("공휴일 API 응답 에러: " + header.path("resultMsg").asText());
                return result;
            }

            // 실제 공휴일 데이터가 담긴 부분 꺼냄
            //{"response":{"body":{"items":{"item":[{"dateName":"1월1일",...}, {"dateName":"설날",...}]}}}}
            JsonNode items = root.path("response").path("body").path("items").path("item");

            // 공휴일이 여러 개면 배열, 딱 1개면 단일 객체로 오는 API 특성 때문에 분기 처리
            if (items.isArray()) {
                for (JsonNode item : items) {
                    result.add(toDTO(item));
                }
            } else if (!items.isMissingNode() && !items.isNull()) {
                result.add(toDTO(items));
            }

        } catch (Exception e) {
            // 네트워크 오류, JSON 파싱 실패 등 예외 상황 처리
            // 이 경우도 result가 빈 리스트로 반환되어 DB에 저장 안 되고, 다음에 재시도 가능
            System.out.println("공휴일 API 호출 실패 (" + year + "): " + e.getMessage());
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
