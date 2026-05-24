package com.study.app.domains.file;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

@Service
public class FileService {
	
	@Autowired
	private Storage storage;
	
	@Value("${spring.cloud.gcp.bucket}")
	private String bucketName;
	
	public Map<String, String> upload(MultipartFile file) throws Exception{
		
		String oriname = file.getOriginalFilename();
        String sysname = UUID.randomUUID() + "_" + oriname;
        
        BlobId blobId = BlobId.of(bucketName, sysname);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());

        Map<String, String> result = new HashMap<>();

        result.put("oriname", oriname);
        result.put("sysname", sysname);

        return result;
	}
	
	// 프로필 사진 출력용 *소용량 전용으로 큰 파일, 게시판 다운로드 사용 xx
	public byte[] profileImage(String sysname) {
		Blob blob = storage.get(BlobId.of(bucketName, sysname));
		
		if(blob == null || !blob.exists()) {
			return null;
		}
		return blob.getContent();
	}


}