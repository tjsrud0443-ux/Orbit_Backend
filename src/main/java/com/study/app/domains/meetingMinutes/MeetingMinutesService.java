package com.study.app.domains.meetingMinutes;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.app.domains.aiChat.AiChatService;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class MeetingMinutesService {
	@Autowired
	private MeetingMinutesDAO minutesDAO;
	
	@Autowired
	private MinutesAttendeesDAO minutesAttendeesDAO;
	
	@Autowired
	private AiChatService aiChatServ;
	
	@Value("${clova.speech.secret-key}")
	private String speechSecretKey;

	@Value("${clova.speech.invoke-url}")
	private String speechInvokeUrl;
	
	@Value("${naver.access-key}")
	private String naverAccessKey;

	@Value("${naver.secret-key}")
	private String naverSecretKey;

	@Value("${naver.object-storage.bucket}")
	private String naverBucket;

	@Value("${groq.api-key}")
	private String groqApiKey;
	
	// Clova Speech STT
	private String transcribe(MultipartFile audioFile) throws Exception {
		String originalFilename = audioFile.getOriginalFilename();
		String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
		String audioKey = "audio_input_" + System.currentTimeMillis() + ext;
		
		S3Client s3 = getNaverS3Client();

		s3.putObject(
			    PutObjectRequest.builder()
			        .bucket(naverBucket)
			        .key(audioKey)
			        .contentType(audioFile.getContentType())
			        .build(),
			    RequestBody.fromBytes(audioFile.getBytes())
			);	       
	    
	    // 3. Clova Speech에 URL로 요청 (/recognizer/url 엔드포인트)
	    String paramsJson = "{"
	        + "\"language\":\"ko-KR\","
	        + "\"completion\":\"async\","
	        + "\"fullText\":true,"
	        + "\"diarization\":{\"enable\":false},"
	        + "\"resultToObs\":true,"
	        + "\"dataKey\":\"" + audioKey + "\""  // url → dataKey
	        + "}";
	    
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(speechInvokeUrl + "/recognizer/object-storage"))  // /upload → /url
	            .header("X-CLOVASPEECH-API-KEY", speechSecretKey)
	            .header("Content-Type", "application/json")
	            .POST(HttpRequest.BodyPublishers.ofString(paramsJson))
	            .build();

	    HttpResponse<String> response = HttpClient.newHttpClient()
	        .send(request, HttpResponse.BodyHandlers.ofString());
	    
	 // Jackson으로 파싱
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode root = mapper.readTree(response.body());
	    String token = root.get("token").asText();
	    
	    // 4. 폴링 후 OBS 음성파일 삭제
	    String result = pollResult(token, audioKey);
	    s3.deleteObject(r -> r.bucket(naverBucket).key(audioKey));
	    
	    return result;
	}
	
	private S3Client getNaverS3Client() {
	    return S3Client.builder()
	        .endpointOverride(URI.create("https://kr.object.ncloudstorage.com"))
	        .credentialsProvider(StaticCredentialsProvider.create(
	            AwsBasicCredentials.create(naverAccessKey, naverSecretKey)))
	        .region(Region.of("kr-standard"))
	        .build();
	}

	private String readResultFromObs(String token, String audioKey) throws Exception {
	    String objectKey = naverBucket + ":" + audioKey + "_" + token + ".json";
	    
	    S3Client s3 = getNaverS3Client();
	    GetObjectRequest getRequest = GetObjectRequest.builder()
	        .bucket(naverBucket)
	        .key(objectKey)
	        .build();
	    
	    byte[] bytes = s3.getObjectAsBytes(getRequest).asByteArray();
	    String content = new String(bytes);
	    System.out.println("OBS 결과: " + content);
	    
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode root = mapper.readTree(content);
	    return root.get("text").asText();
	}
	
	private String pollResult(String token, String audioKey) throws Exception {
	    String statusUrl = speechInvokeUrl + "/recognizer/" + token;

	    for (int i = 0; i < 30; i++) {  // 최대 30번 (약 5분)
	        Thread.sleep(10000);  // 10초 대기

	        HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(statusUrl))
	            .header("X-CLOVASPEECH-API-KEY", speechSecretKey)
	            .GET()
	            .build();

	        HttpResponse<String> response = HttpClient.newHttpClient()
	            .send(request, HttpResponse.BodyHandlers.ofString());

	        System.out.println("폴링 응답: " + response.body());

	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode root = mapper.readTree(response.body());
	        String result = root.get("result").asText();

	        if ("COMPLETED".equals(result)) {
	            return readResultFromObs(token, audioKey);
	        } else if ("ERROR".equals(result)) {
	            throw new Exception("STT 처리 실패: " + root.get("message").asText());
	        }
	        // RUNNING이면 계속 대기
	    }
	    throw new Exception("STT 처리 시간 초과");
	}
	
	private byte[] concat(byte[]... arrays) {
	    int total = 0;
	    for (byte[] a : arrays) total += a.length;
	    byte[] result = new byte[total];
	    int pos = 0;
	    for (byte[] a : arrays) {
	        System.arraycopy(a, 0, result, pos, a.length);
	        pos += a.length;
	    }
	    return result;
	}
	
	private String[] extractSummary(String transcript) throws Exception {
	    String prompt = "다음 회의 내용을 분석해서 아래 JSON 형식으로만 반환해줘. 다른 말은 하지 마.decisions와 todos 모두 반드시 문자열로 반환해.\n"
	        + "{\"decisions\": \"결정된 사항\", \"todos\": \"할 일 목록\"}\n\n"
	        + "회의 내용: " + transcript;

	    String requestBody = "{"
	        + "\"model\": \"llama-3.1-8b-instant\","
	        + "\"messages\": [{\"role\": \"user\", \"content\": \"" 
	        + prompt.replace("\"", "\\\"").replace("\n", "\\n") 
	        + "\"}]"
	        + "}";

	    HttpRequest request = HttpRequest.newBuilder()
	        .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
	        .header("Authorization", "Bearer " + groqApiKey)
	        .header("Content-Type", "application/json")
	        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	        .build();

	    HttpResponse<String> response = HttpClient.newHttpClient()
	        .send(request, HttpResponse.BodyHandlers.ofString());

	    System.out.println("Groq 응답: " + response.body());

	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode root = mapper.readTree(response.body());

	    if (root.has("error")) {
	        return new String[]{"", ""};
	    }

	    String text = root.path("choices").get(0)
	        .path("message").path("content").asText();

	    text = text.replace("```json", "").replace("```", "").trim();
	    JsonNode result = mapper.readTree(text);

	    String decisions = result.get("decisions").asText();

	    JsonNode todosNode = result.get("todos");
	    String todos;
	    if (todosNode.isArray()) {
	        StringBuilder sb = new StringBuilder();
	        for (JsonNode item : todosNode) {
	            sb.append(item.asText()).append("\n");
	        }
	        todos = sb.toString().trim();
	    } else {
	        todos = todosNode.asText();
	    }

	    return new String[]{decisions, todos};
	}
	
	// STT + AI 요약
	public Map<String, String> sttAndSummary(MultipartFile audioFile) throws Exception {
	    String transcript = transcribe(audioFile);
	    String[] summary = extractSummary(transcript);
	    
	    Map<String, String> result = new HashMap<>();
	    result.put("transcript", transcript);
	    result.put("decisions", summary[0]);
	    result.put("todos", summary[1]);
	    return result;
	}
	
	@Transactional
	public int insertMinutes(MeetingMinutesDTO dto) {
	    int result = minutesDAO.insertMinutes(dto);  // insert 후 dto.minuteSeq에 자동으로 생성된 seq 담김
	    
	    if (dto.getAttendees() != null) {
	        for (MinutesAttendeesDTO attendee : dto.getAttendees()) {
	        	 attendee.setMinute_seq(dto.getMinute_seq()); 
	        	 minutesAttendeesDAO.insertMinutesAttendees(attendee);            
	        }
	    }
	    aiChatServ.createMeetingChunk(dto);
	    return result;		
	}
	
	public List<MeetingMinutesDTO> getMinutesList() {
		return minutesDAO.getMinutesList();
	}
	
	public MeetingMinutesDTO getMinutesDetail(Long minute_seq) {
		return minutesDAO.getMinutesDetail(minute_seq);
	}
	
	@Transactional
	public void deleteMinutesAll(Long minute_seq) {
		aiChatServ.deleteMeetingRag(minute_seq);
		
		// 1. 자식 테이블(참석자) 데이터 먼저 삭제
		minutesAttendeesDAO.deleteMinutesAttendees(minute_seq);
	    
	    // 2. 부모 테이블(회의록) 데이터 최종 삭제
	    minutesDAO.deleteMinutes(minute_seq);
	}
	
	//전부 성공하면 commit, 하나라도 실패하면 전부 rollback
	@Transactional 
	public void updateMinutesAll(MeetingMinutesDTO dto) {
	    // 1. 회의록 본문 내용 수정 (제목, 내용 등 수정)
	    minutesDAO.updateMinutes(dto); 
	    aiChatServ.deleteMeetingRag(dto.getMinute_seq());
	    aiChatServ.createMeetingChunk(dto);
	    // 2. 기존 참석자 싹 지우기
	    minutesAttendeesDAO.deleteMinutesAttendees(dto.getMinute_seq()); 
	    
	    // 3. 처음 등록할 때 쓰던 insert 쿼리를 그대로 재사용해서 새로 넣기
	    if (dto.getAttendees() != null) {
	        for (MinutesAttendeesDTO emp : dto.getAttendees()) {
	        	// 꺼내온 emp에 현재 수정 중인 회의록 번호(minute_seq)를 심어줌
	            emp.setMinute_seq(dto.getMinute_seq());
	            
	            //  DTO 객체인 emp를 그대로 매개변수로
	            minutesAttendeesDAO.insertMinutesAttendees(emp);
	        }
	    }
	}
	
	@Transactional
	public void updateMinutesShare(Long minute_seq, String is_shared) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("minute_seq", minute_seq);
	    params.put("is_shared", is_shared);
	    minutesDAO.updateMinutesShare(params);
	}
}
