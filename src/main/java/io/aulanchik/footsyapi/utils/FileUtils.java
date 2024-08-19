package io.aulanchik.footsyapi.utils;

import io.aulanchik.footsyapi.entities.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

public class FileUtils {

    public static File createFileEntity(MultipartFile file, String fileName, String filePath, long fileSize) {
        File entity = new File();
        entity.setCreatedAt(new Date());
        entity.setFilePath(filePath);
        entity.setFileType(file.getContentType());
        entity.setFileUrl("/files/" + fileName);
        entity.setName(fileName);
        entity.setFileSize(fileSize);
        return entity;
    }

    public static String getFileExtension(MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();

        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new IllegalArgumentException("Invalid file name");
        }

        int lastDotIndex = originalFileName.lastIndexOf('.');

        if (lastDotIndex == -1 || lastDotIndex == originalFileName.length() - 1) {
            throw new IllegalArgumentException("File does not have a valid extension");
        }

        return originalFileName.substring(lastDotIndex + 1);
    }

    public static String generateFileName(String fileExtension) {
        return "product__" + UUID.randomUUID() + "size___original." + fileExtension;
    }
}
