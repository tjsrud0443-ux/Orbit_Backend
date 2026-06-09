package com.study.app.domains.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.file.FileService;

@RestController
@RequestMapping("/board")
public class BoardController {
	 @Autowired
	 private BoardService boardServ;
	 
	 @Autowired
	 private FileService fileService; 
	 
 // 에디터 이미지 업로드 (Quill 이미지 선택 즉시 호출)
    @PostMapping("/imageUpload")
    public ResponseEntity<Map<String, String>> uploadEditorImage(
            @RequestParam("image") MultipartFile image) {
        try {
            String url = boardServ.uploadEditorImage(image);
            return ResponseEntity.ok(Map.of("url", url));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "이미지 업로드 실패"));
        }
    }

    // 게시글 등록
    @PostMapping
    public ResponseEntity<String> writePost(
    		@RequestAttribute String loginId,
            @RequestParam("category") String category,
            @RequestParam("title")    String title,
            @RequestParam("content")  String content,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        try {
            BoardPostsDTO post = new BoardPostsDTO();
            post.setCategory(category);
            post.setTitle(title);
            post.setContent(content);
            post.setUsers_id(loginId);

            boardServ.writePost(post, files);
            return ResponseEntity.status(HttpStatus.CREATED).body("게시글이 등록되었습니다.");

        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("게시글 등록 중 오류가 발생했습니다.");
        }
    }
    
    /*댓글*/
    @PostMapping("/{post_seq}/comments")
    public ResponseEntity<String> insertComment(
            @PathVariable Long post_seq,
            @RequestAttribute String loginId,
            @RequestBody BoardCommentsDTO comment) {
        try {
            comment.setPost_seq(post_seq);
            comment.setUsers_id(loginId);
            boardServ.insertComment(comment);
            return ResponseEntity.ok("댓글이 등록되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("댓글 등록 중 오류가 발생했습니다.");
        }
    }
    
	@GetMapping("/editorImage/{sysname}")
	public ResponseEntity<byte[]> getImage(@PathVariable String sysname) throws Exception {
		System.out.println("이미지 요청: " + sysname);  // ← 추가
	    byte[] imageBytes = fileService.getFileBytes(sysname);
	    if (imageBytes == null) {
	        return ResponseEntity.notFound().build();
	    }
	    System.out.println("imageBytes: " + (imageBytes == null ? "null" : imageBytes.length));
	    // 확장자로 Content-Type 추정
	    String contentType = "image/jpeg";
	    if (sysname.toLowerCase().endsWith(".png")) contentType = "image/png";
	    else if (sysname.toLowerCase().endsWith(".gif")) contentType = "image/gif";
	    else if (sysname.toLowerCase().endsWith(".webp")) contentType = "image/webp";
	    else if (sysname.toLowerCase().endsWith(".jpg")) contentType = "image/jpeg";
	    
	    return ResponseEntity.ok()
	            .header("Content-Type", contentType)
	            .body(imageBytes);
	}
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getBoardList(
    		@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword){
    	
    	Map<String, Object> params = new HashMap<>();
	    params.put("keyword", keyword);
	    params.put("start", (page - 1) * size + 1);  // 1, 11, 21...
	    params.put("end", page * size);               // 10, 20, 30...

	    List<BoardPostsDTO> list = boardServ.getBoardList(params);
	    int total = boardServ.getBoardCount(params);

	    Map<String, Object> result = new HashMap<>();
	    result.put("list", list);
	    result.put("total", total);
	    result.put("totalPages", (int) Math.ceil((double) total / size));

	    return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{post_seq}")
    public ResponseEntity<BoardPostsDTO> getPostDetail(@PathVariable Long post_seq){
    	BoardPostsDTO detail = boardServ.getPostDetail(post_seq);
    	return ResponseEntity.ok(detail);
    }
    
    //파일 다운
    @GetMapping("/download/{fileSeq}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileSeq) throws Exception {
        BoardFileDTO file = boardServ.getFileBySeq(fileSeq);
        byte[] fileBytes = fileService.getFileBytes(file.getFile_sysname());
        
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + 
                    java.net.URLEncoder.encode(file.getFile_oriname(), "UTF-8") + "\"")
                .header("Content-Type", "application/octet-stream")
                .body(fileBytes);
    }
    
    @DeleteMapping("/{post_seq}")
    public ResponseEntity<String> deletePost(@PathVariable Long post_seq) {
        try {
            boardServ.deletePost(post_seq);
            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("삭제 중 오류가 발생했습니다.");
        }
    }
    
    @DeleteMapping("/comments/{comment_seq}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long comment_seq){
         boardServ.deleteComment(comment_seq);
         return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{post_seq}")
    public ResponseEntity<String> updatePost(
            @PathVariable Long post_seq,
            @RequestAttribute String loginId,
            @RequestParam("category") String category,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "newFiles", required = false) List<MultipartFile> newFiles,
            @RequestParam(value = "deletedFileSeqs", required = false) List<Long> deletedFileSeqs,
            @RequestParam(value = "deletedImageUrls", required = false) List<String> deletedImageUrls) {
        try {
            BoardPostsDTO post = new BoardPostsDTO();
            post.setPost_seq(post_seq);
            post.setCategory(category);
            post.setTitle(title);
            post.setContent(content);

            boardServ.updatePost(post, newFiles, deletedFileSeqs,deletedImageUrls);
            return ResponseEntity.ok("게시글이 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("수정 중 오류가 발생했습니다.");
        }
    }
    
    @PutMapping("/comments/{comment_seq}")
    public ResponseEntity<Void> updateComment(@PathVariable Long comment_seq, 
    										  @RequestBody BoardCommentsDTO dto){
    	dto.setComment_seq(comment_seq);
    	boardServ.updateComment(dto);
    	return ResponseEntity.ok().build();
    }
}
