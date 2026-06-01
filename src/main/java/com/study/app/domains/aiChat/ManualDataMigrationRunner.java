package com.study.app.domains.aiChat;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

//@Slf4j
@Component
@RequiredArgsConstructor
public class ManualDataMigrationRunner implements CommandLineRunner{
	private static final Logger log = LoggerFactory.getLogger(ManualDataMigrationRunner.class);
	@Autowired
	private QdrantVectorStore vectorStore;
	@Autowired
	private ResourcePatternResolver resolver;
	
	@Override
	public void run(String... args) throws Exception{
//		executeMigration();
	}
	
	private void executeMigration() {
		try {
			log.info("=== 기존 마크다운 수동 문서 적재(Migration) 시작 ===");
			
			// migration 폴더 내 txt 파일 가져오기
			Resource[] resources = resolver.getResources("classpath:migration/*.txt");
			
			if(resources.length == 0) {
				log.warn("마이그레이션 폴더 내에 처리할 txt 파일이 없음.");
				return;
			}
			
			for(Resource resource : resources) {
				String fileName = resource.getFilename();
				String documentId = "MANUAL_" + UUID.randomUUID().toString().substring(0, 8);
				
				log.info("파일 분석 중: {}", fileName);
				
				// 파일 본문 읽기
				String content = StreamUtils.copyToString(resource.getInputStream(), 
						StandardCharsets.UTF_8);
				
				// md 형식 txt 파일 문단 기준으로 분리
				String[] splitParagraphs = content.split("\n\n");
				List<Document> documentList = new ArrayList<>();
				
				for(int i = 0; i < splitParagraphs.length; i++) {
					String paragraph = splitParagraphs[i].trim();
					if(paragraph.isEmpty()) continue;
					
					// 메타데이터 맵 구성 (추후 문서 필터링에 필요)
					Map<String, Object> metadata = new HashMap<>();
					metadata.put("rag_doc_seq", "MANUAL_" + fileName);
					metadata.put("source_type", "DOCUMENTS");
					metadata.put("file_name", fileName);
					metadata.put("chunk_index", i);
					
					// Spring AI Document 객체 생성 (text + metadata)
					Document doc = new Document(paragraph, metadata);
					documentList.add(doc);
				}
				// Spring AI Qdrant에 추가
				// 내부적으로 Qdrant 라이브러리가 동작하지만, 임베딩 모델이 로컬 Docker bge-m3로 커스텀 처리를 해줘야 하므로, 설정 커스텀 클래스를 만들어 연결
				vectorStore.add(documentList);
				log.info("파일 적재 완료 [청크 개수: {}] : {}", documentList.size(), fileName);
			}
			
			log.info("=== 수동 마이그레이션 성공 ===");
			
		}catch(Exception e) {
			log.error("마이그레이션 중 오류 발생: ", e);
		}
	}
}