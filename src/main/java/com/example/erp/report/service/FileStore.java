package com.example.erp.report.service;

import com.example.erp.report.dto.UploadFile;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileStore {

    String userHome = System.getProperty("user.home");
    String desktopPath = userHome + File.separator + "Desktop";
    String fileDir = desktopPath + File.separator + "erp_save";
    public String getFullPath(String filename) {
        ensureDirectoryExists();  // 디렉터리가 없으면 생성
        System.out.println("여기"  + fileDir+ File.separator + filename);
        return fileDir + File.separator + filename;
    }

    private void ensureDirectoryExists() {
        Path directoryPath = Paths.get(fileDir);
        if (Files.notExists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                e.printStackTrace();  // 적절한 예외 처리를 수행하도록 수정하세요.
            }
        }
    }


    public UploadFile storeFile(MultipartFile multipartFile) throws IOException{
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}