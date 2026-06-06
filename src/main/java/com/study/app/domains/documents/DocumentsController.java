package com.study.app.domains.documents;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documents")
public class DocumentsController {
	
	@Autowired
	private DocumentsService docServ;
	
	@GetMapping("getAllDocs")
	public ResponseEntity<List<DocumentsDTO>> getAllDocs(){
		List<DocumentsDTO> resp = docServ.getAllDocs();
		return ResponseEntity.ok(resp);
	}
	
	@GetMapping("getFavorites")
	public ResponseEntity<List<DocumentBookmarksDTO>> getFavorites(@RequestAttribute String loginId){
		List<DocumentBookmarksDTO> resp = docServ.getFavorites(loginId);
		return ResponseEntity.ok(resp);
	}
	
	@PostMapping("addFavorite/{document_seq}")
	public ResponseEntity<Void> addFavorite(@PathVariable Long document_seq,
											@RequestAttribute String loginId){
		docServ.addFavorite(document_seq, loginId);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("removeFavorite/{document_seq}")
	public ResponseEntity<Void> removeFavorite(@PathVariable Long document_seq,
												@RequestAttribute String loginId){
		docServ.removeFavorite(document_seq, loginId);
		return ResponseEntity.ok().build();
	}
}
