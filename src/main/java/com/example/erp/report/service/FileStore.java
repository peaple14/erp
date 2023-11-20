package com.example.erp.report.service;

import com.example.erp.report.dto.UploadFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class FileStore {

    static String userHome = System.getProperty("user.home");
    static String desktopPath = userHome + File.separator + "Desktop";
    static String fileDir = desktopPath + File.separator + "erp_save";

    public static String getFullPath(String filename) {
        ensureDirectoryExists();  // 디렉터리가 없으면 생성
        return fileDir + File.separator + filename;
    }

    private static void ensureDirectoryExists() {
        Path directoryPath = Paths.get(fileDir);
        if (Files.notExists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //파일저장
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename, storeFileName);
    }

    //파일저장용 이름생성
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public void deleteFile(String fileName) {
        File file = new File(getFullPath(fileName));
        if (!file.exists()) {
            return;
        }
        // 파일 또는 디렉토리 삭제
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    deleteFile(subFile.getAbsolutePath());
                }
            }
        }
        if (!file.delete()) {
            System.err.println("Failed to delete file: " + fileName);
        }
    }

    public static boolean compareFilesByHash(MultipartFile multipartFile, String fileName)
            throws NoSuchAlgorithmException, IOException {
        String hash1 = calculateFileHash(multipartFile);
        String hash2 = calculateFileHash(fileDir + File.separator + fileName);
        return hash1.equals(hash2);
    }

    private static String calculateFileHash(MultipartFile multipartFile)
            throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        try (DigestInputStream dis = new DigestInputStream(multipartFile.getInputStream(), md)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = dis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
        }

        byte[] hashBytes = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    private static String calculateFileHash(String filePath)
            throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        try (DigestInputStream dis = new DigestInputStream(Files.newInputStream(Path.of(filePath)), md)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = dis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
        }
        byte[] hashBytes = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

}