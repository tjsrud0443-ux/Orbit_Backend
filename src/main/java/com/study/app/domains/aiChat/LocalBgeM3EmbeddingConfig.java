package com.study.app.domains.aiChat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.AbstractEmbeddingModel;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class LocalBgeM3EmbeddingConfig {
	
	@Bean
    @Primary // 프로젝트 내 기본 임베딩 모델로 bge-m3를 지정
    public org.springframework.ai.embedding.EmbeddingModel embeddingModel() {
        return new AbstractEmbeddingModel() {
            private final RestClient restClient = RestClient.builder().baseUrl("http://localhost:8000").build();
            // ㄴ bge-m3 도커 서버와 통신하는 RestClient
            
            @Override
            public float[] embed(Document document) {
                Map<String, String> body = Map.of("inputs", document.getFormattedContent());
                return restClient.post()
                        .uri("/embed")
                        .body(body)
                        .retrieve()
                        .body(float[].class);
            }

            // 기존 call 메서드도 신버전 스펙에 맞게 수정
            @Override
            public EmbeddingResponse call(EmbeddingRequest request) {
                List<Embedding> embeddings = new ArrayList<>();
                List<String> instructions = request.getInstructions();

                for (int i = 0; i < instructions.size(); i++) {
                    String text = instructions.get(i);
                    
                    // Docker TEI bge-m3 서버로 API 규격 맞춰 요청
                    Map<String, String> body = Map.of("inputs", text);
                    
                    // TEI 서버는 POST /embed 시 float[] 형태의 1024차원 배열을 리턴함
                    float[] vector = restClient.post()
                            .uri("/embed")
                            .body(body)
                            .retrieve()
                            .body(float[].class);

                    embeddings.add(new Embedding(vector, i));
                }

                return new EmbeddingResponse(embeddings, new EmbeddingResponseMetadata());
            }
        };
    }
}
