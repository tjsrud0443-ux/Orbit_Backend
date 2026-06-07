package com.study.app.domains.board;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.file.FileService;

@Service
public class BoardService {
	@Autowired
	private BoardDAO boardDAO;
	
    @Autowired
    private FileService fileService; 
	   
	@Transactional //여러 DB 작업을 하나로 묶어주는 어노테이션
	public void writePost(BoardPostsDTO post, List<MultipartFile> files) throws Exception {

	    // 게시글 저장
	    boardDAO.insertPost(post);

	    // 파일 저장 (게시글 저장 실패 시 여기까지 안 옴)
	    if (files != null && !files.isEmpty()) {
	        for (MultipartFile file : files) {
	            if (file.isEmpty()) continue;

	            Map<String, String> fileInfo = fileService.upload(file);

	            BoardFileDTO fileDTO = new BoardFileDTO();
	            fileDTO.setPost_seq(post.getPost_seq()); // selectKey로 세팅된 post_seq 사용
	            fileDTO.setFile_oriname(fileInfo.get("oriname"));
	            fileDTO.setFile_sysname(fileInfo.get("sysname"));
	            fileDTO.setFile_path(fileInfo.get("file_path"));
	            fileDTO.setFile_size(file.getSize());

	            boardDAO.insertPostFile(fileDTO);
	        }
	    }
	}
	// 에디터 이미지 업로드
    public String uploadEditorImage(MultipartFile image) throws Exception {
        Map<String, String> fileInfo = fileService.upload(image);
        String sysname = fileInfo.get("sysname");
//        return fileInfo.get("file_path"); // GCS URL만 반환
        return "http://localhost/board/editorImage/" + sysname;
    }
    
    /*출력*/
    public List<BoardPostsDTO> getBoardList(Map<String, Object> params){
    	return boardDAO.getBoardList(params);
    }
    
    public int getBoardCount(Map<String, Object> params) {
        return boardDAO.getBoardCount(params);
    }
    
    public BoardPostsDTO getPostDetail(Long post_seq) {
    	return boardDAO.getPostDetail(post_seq);
    }
    
    /*삭제*/
    @Transactional
    public void deletePost(Long post_seq) throws Exception {
        // 에디터 이미지 삭제
        BoardPostsDTO post = boardDAO.getPostDetail(post_seq);
        if (post.getContent() != null) {
            java.util.regex.Pattern pattern = java.util.regex.Pattern
                .compile("/board/editorImage/([^\"]+)");
            java.util.regex.Matcher matcher = pattern.matcher(post.getContent());
           // matcher.find()로 이미지를 하나씩 찾아서 matcher.group(1)로 파일명만 추출
            while (matcher.find()) {
                fileService.deleteFromGCS(matcher.group(1));
            }
        }
        
        // 1. 파일 목록 조회
        List<BoardFileDTO> files = boardDAO.getFilesByPostSeq(post_seq);

        // 2. GCS에서 파일 삭제
        for (BoardFileDTO file : files) {
            fileService.deleteFromGCS(file.getFile_sysname());
        }

        // 3. BOARD_FILE 삭제
        boardDAO.deleteFiles(post_seq);

        // 4. BOARD_POSTS 삭제
        boardDAO.deletePost(post_seq);
    }
    
    @Transactional
    public void updatePost(BoardPostsDTO post, List<MultipartFile> newFiles, List<Long> deletedFileSeqs) throws Exception {
        
        // 1. 게시글 수정
        boardDAO.updatePost(post);
        
        // 2. 삭제된 첨부파일 처리
        if (deletedFileSeqs != null && !deletedFileSeqs.isEmpty()) {
            for (Long fileSeq : deletedFileSeqs) {
                BoardFileDTO file = boardDAO.getFileBySeq(fileSeq);
                fileService.deleteFromGCS(file.getFile_sysname());
                boardDAO.deleteFile(fileSeq);
            }
        }
        
        // 3. 새 첨부파일 추가
        if (newFiles != null && !newFiles.isEmpty()) {
            for (MultipartFile file : newFiles) {
                if (file.isEmpty()) continue;
                Map<String, String> fileInfo = fileService.upload(file);
                BoardFileDTO fileDTO = new BoardFileDTO();
                fileDTO.setPost_seq(post.getPost_seq());
                fileDTO.setFile_oriname(fileInfo.get("oriname"));
                fileDTO.setFile_sysname(fileInfo.get("sysname"));
                fileDTO.setFile_path(fileInfo.get("file_path"));
                fileDTO.setFile_size(file.getSize());
                boardDAO.insertPostFile(fileDTO);
            }
        }
    }
}
