package io.aulanchik.footsyapi.services;

import io.aulanchik.footsyapi.entities.File;
import io.aulanchik.footsyapi.exceptions.FileStorageException;
import io.aulanchik.footsyapi.repositories.FileRepository;
import io.aulanchik.footsyapi.utils.FileUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Slf4j
public class FileService {

    private final String baseDir = ".\\products";
    @Autowired
    private FileRepository fileRepository;

    public FileService() throws IOException {
        Path path = Paths.get(baseDir);
        boolean dirExists = Files.exists(path.toAbsolutePath().normalize());

        if (!dirExists) {
            Files.createDirectory(path.toAbsolutePath().normalize());
        }
    }

    public File storeFiles(MultipartFile multipartFile) {
        try {
            String fileExt = FileUtils.getFileExtension(multipartFile);
            String fileName = FileUtils.generateFileName(fileExt);
            Path filePath = Paths.get(baseDir.concat("/").concat(fileName)).toAbsolutePath().normalize();
            long fileSize = Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            File file = FileUtils.createFileEntity(multipartFile, fileName, filePath.toString(), fileSize);

            return fileRepository.save(file);

        } catch (IOException ex) {
            throw new FileStorageException("Unable to save file {} " + multipartFile.getOriginalFilename());
        }

    }

    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
        return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
    }

    public ResponseEntity<?> getFile(String filename, HttpServletRequest request) {
        try {
            File file = fileRepository.findByName(filename);
            Path path = Paths.get(file.getFilePath()).toAbsolutePath().normalize();
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                String contentType = request
                        .getServletContext()
                        .getMimeType(
                                resource.getFile().getAbsolutePath()
                        );

                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS).cachePrivate().proxyRevalidate())
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            }

        } catch (IOException ex) {
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ResponseEntity.notFound().build();
    }

}
