package com.study.app.domains.aiChat;

import java.util.List;
import java.util.Map;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DBTool {

	@Autowired
	private JdbcTemplate jdbc;

	@Tool(description="""
			오라클 11g 데이터베이스에 SELECT 조회 쿼리만 실행하고 결과를 반환합니다.
			INSERT, UPDATE, DELETE, DROP, ALTER, CREATE 문은 절대 사용할 수 없고, 
			오로지 SELECT 조회 쿼리만 사용할 수 있습니다.
			사용자의 질문에 필요한 데이터를 조회할 때만 사용하세요.
			""")
	public String executeDBQuery(String sql) {
		
		if(sql == null || sql.isBlank()) {
			return "SQL이 비어있습니다.";
		}

		String normalized = sql.trim().toLowerCase();
		
		if (!normalized.contains("v_ai_")) {
		    return "AI 챗봇은 V_AI_로 시작하는 VIEW만 조회할 수 있습니다.";
		}
		
		if(normalized.endsWith(";")) {
			sql = sql.trim().substring(0, sql.trim().length() - 1);
			normalized = sql.toLowerCase();
		}
		
		if (!(normalized.startsWith("select"))) {
            return "SELECT 조회 쿼리만 실행할 수 있습니다.";
        }
		
		if(normalized.contains("--") || normalized.contains("/*") || normalized.contains("*/")) {
			return "SQL 주석은 사용할 수 없습니다.";
		}
		
		String[] blockedKeywords = {
                "insert", "update", "delete", "merge",
                "drop", "alter", "truncate", "create",
                "grant", "revoke", "commit", "rollback"
        };

        for (String keyword : blockedKeywords) {
        	 if (normalized.matches(".*\\b" + keyword + "\\b.*")) {
                 return "허용되지 않은 SQL 키워드가 포함되어 있습니다: " + keyword;
             }
        }
        
        try {
        	String limitedSql = "SELECT * FROM (" + sql + ") WHERE ROWNUM <= 30";
        	List<Map<String,Object>> result = jdbc.queryForList(limitedSql);

			if(result == null || result.isEmpty()) {
				return "조회된 데이터가 없습니다.";
			}

			return result.toString();
			
        }catch(Exception e) {
        	e.printStackTrace();
        	return "Query문 오류 발생" + e.getMessage() 
        	+ "\n위 오류를 참고해서 SQL문을 수정한 뒤 다시 조회하세요.";
        }
	}
}
