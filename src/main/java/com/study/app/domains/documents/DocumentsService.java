package com.study.app.domains.documents;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.file.FileService;

@Service
public class DocumentsService {
	
	@Autowired
	private DocumentsDAO dao;
	@Autowired
	private FileService fileServ;
	
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

        } catch (Exception e) {
            throw new RuntimeException("문서 및 파일 등록 실패: " + e.getMessage(), e);
        }
    }
}
