package com.study.app.domains.board;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	/*등록*/
    public int insertPost(BoardPostsDTO dto) {
    	return mybatis.insert("Board.insertPost",dto);
    }

    public int insertPostFile(BoardFileDTO dto) {
    	return mybatis.insert("Board.insertPostFile",dto);
    }
    /*출력*/
    public List<BoardPostsDTO> getBoardList(Map<String, Object> params){
    	return mybatis.selectList("Board.getBoardList",params);
    }
    
    public int getBoardCount(Map<String, Object> params) {
    	return mybatis.selectOne("Board.getBoardCount",params);
    }
    
    public BoardPostsDTO getPostDetail(Long post_seq) {
    	return mybatis.selectOne("Board.getPostDetail", post_seq);
    }
    /*삭제*/
    public List<BoardFileDTO> getFilesByPostSeq(Long post_seq) {
        return mybatis.selectList("Board.getFilesByPostSeq", post_seq);
    }

    public void deleteFiles(Long post_seq) {
        mybatis.delete("Board.deleteFiles", post_seq);
    }

    public void deletePost(Long post_seq) {
        mybatis.delete("Board.deletePost", post_seq);
    }
    
    /*수정*/
    public void updatePost(BoardPostsDTO post) {
        mybatis.update("Board.updatePost", post);
    }
    
    public BoardFileDTO getFileBySeq(Long fileSeq) {
        return mybatis.selectOne("Board.getFileBySeq", fileSeq);
    }
    
    public void deleteFile(Long fileSeq) {
        mybatis.delete("Board.deleteFile", fileSeq);
    }
}
