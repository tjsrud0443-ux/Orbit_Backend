package com.study.app.domains.aiChat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

//@Component
@RequiredArgsConstructor
public class QdrantCloudMigrationRunner implements CommandLineRunner{
	
	@Autowired
	private RagDAO ragDao;

	private final RestClient restClient =
	        RestClient.builder()
	                .baseUrl("http://localhost:8000")
	                .build();
	
	@Override
	public void run(String... args) {

	    List<RagChunksDTO> chunks =
	            ragDao.findAllChunks();

	    for (RagChunksDTO chunk : chunks) {

	        Map<String, Object> body =
	                new HashMap<>();

	        body.put("chunk_seq",
	                chunk.getChunk_seq());

	        body.put("rag_doc_seq",
	                chunk.getRag_doc_seq());

	        body.put("file_name",
	                "manual");

	        body.put("chunk_text",
	                chunk.getChunk_text());

	        restClient.post()
	                .uri("/upsert")
	                .body(body)
	                .retrieve()
	                .toBodilessEntity();

	        System.out.println(
	                "업로드 완료 : "
	                + chunk.getChunk_seq());
	    }
	}
}
