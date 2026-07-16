package com.study.app.domains.documents;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.aiChat.AiChatService;
import com.study.app.domains.file.FileService;

@Service
public class DocumentsService {

	@Autowired
	private DocumentsDAO dao;
	@Autowired
	private FileService fileServ;
	@Autowired
	private AiChatService aiChatServ;

	@Transactional
	public void addDoc(String title, String users_id, MultipartFile file) {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("업로드할 파일이 존재하지 않습니다.");
		}

		try {
			DocumentsDTO docDTO = new DocumentsDTO();
			docDTO.setTitle(title);
			docDTO.setUsers_id(users_id);

			dao.addDoc(docDTO);
			Long document_seq = docDTO.getDocument_seq();

			Map<String, String> uploadResult = fileServ.upload(file);

			String oriname = uploadResult.get("oriname");
			String sysname = uploadResult.get("sysname");
			String file_path = uploadResult.get("file_path");
			String mime_type = file.getContentType();


			DocumentsFilesDTO docFileDTO = new DocumentsFilesDTO();
			docFileDTO.setDocument_seq(document_seq);
			docFileDTO.setFile_oriname(oriname);
			docFileDTO.setFile_sysname(sysname);
			docFileDTO.setFile_path(file_path);
			docFileDTO.setMime_type(mime_type);

			dao.insertDocFile(docFileDTO);

			Long file_seq = docFileDTO.getFile_seq();

			if(isAiFile(oriname)) {	
				String signedUrl = fileServ.createSignedUrl(sysname);
				aiChatServ.createChunk(file_seq, document_seq, oriname, signedUrl, mime_type);
			}

		} catch (Exception e) {
			throw new RuntimeException("문서 및 파일 등록 실패: " + e.getMessage(), e);
		}
	}

	@Transactional
	public void editDoc(Long document_seq, String title, MultipartFile file) throws Exception {
		dao.updateDocumentTitle(document_seq, title);

		if (file != null && !file.isEmpty()) {
			aiChatServ.deleteDocumentRag(document_seq);
			DocumentsFilesDTO oldFile = dao.findFileByDocSeq(document_seq);
			if (oldFile != null) {
				fileServ.deleteFromGCS(oldFile.getFile_sysname());
				dao.deleteDocFile(oldFile.getFile_seq());
			}

			Map<String, String> uploadResult = fileServ.upload(file);

			DocumentsFilesDTO docFileDTO = new DocumentsFilesDTO();
			docFileDTO.setDocument_seq(document_seq);
			docFileDTO.setFile_oriname(uploadResult.get("oriname"));
			docFileDTO.setFile_sysname(uploadResult.get("sysname"));
			docFileDTO.setFile_path(uploadResult.get("file_path"));
			docFileDTO.setMime_type(file.getContentType());

			dao.insertDocFile(docFileDTO);

			Long file_seq = docFileDTO.getFile_seq();

			if(isAiFile(docFileDTO.getFile_oriname())) {	
				String signedUrl = fileServ.createSignedUrl(docFileDTO.getFile_sysname());
				aiChatServ.createChunk(file_seq, document_seq, docFileDTO.getFile_oriname(), signedUrl, docFileDTO.getMime_type());
			}
		}
	}

	@Transactional
	public void deleteDoc(Long document_seq) {
		DocumentsFilesDTO fileDTO = dao.findFileByDocSeq(document_seq);
		if (fileDTO != null) {
			fileServ.deleteFromGCS(fileDTO.getFile_sysname());
		}
		aiChatServ.deleteDocumentRag(document_seq);
		dao.deleteFavorDocs(document_seq);
		dao.deleteDocFileByDocSeq(document_seq);
		dao.deleteDocument(document_seq);
	}

	public List<DocumentsDTO> getAllDocs(){
		return dao.getAllDocs();
	}

	public List<DocumentBookmarksDTO> getFavorites(String loginId){
		return dao.getFavorites(loginId);
	}

	public void addFavorite(Long document_seq, String loginId) {
		dao.addFavorite(document_seq, loginId);
	}

	public void removeFavorite(Long document_seq, String loginId) {
		dao.removeFavorite(document_seq, loginId);
	}

	private boolean isAiFile(String oriName) {
		if(oriName == null || oriName.isBlank()) {
			return false;
		}

		String lowerFileName = oriName.toLowerCase();

		boolean aiExtension = 
				lowerFileName.endsWith(".pdf")
				|| lowerFileName.endsWith(".doc")
				|| lowerFileName.endsWith(".docx");

		if(!aiExtension) {
			return false;
		}
		return true;
	}

}
