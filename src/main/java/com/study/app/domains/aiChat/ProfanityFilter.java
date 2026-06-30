package com.study.app.domains.aiChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProfanityFilter {
	
	private final List<String> blockWords;
	
	public ProfanityFilter(
			@Value("classpath:ai/profanity_words.txt") Resource resource) {
		this.blockWords = loadWords(resource);
	}
	
	public boolean containsProfanity(String text) {
		if(text == null || text.isBlank()) {
			return false;
		}
		
		String normalizedText = normalize(text);
		
		return blockWords.stream().anyMatch(normalizedText::contains);
	}
	
	private List<String> loadWords(Resource resource) {
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(resource.getInputStream(),
						StandardCharsets.UTF_8))) {
			
			return br.lines()
					.map(String::trim)
					.filter(line -> !line.isBlank())
					.filter(line -> !line.startsWith("#"))
					.map(this::normalize)
					.distinct()
					.toList();
			
		}catch(Exception e) {
			e.printStackTrace();
			return List.of();
		}
	}
	
	private String normalize(String text) {
		return text.toLowerCase(Locale.ROOT)
				.replaceAll("[\\s\\p{Punct}]+", "");
	}
}