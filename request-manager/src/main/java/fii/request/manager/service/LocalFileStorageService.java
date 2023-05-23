package fii.request.manager.service;

import fii.request.manager.dto.EditableHtmlDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalFileStorageService {

    private final String uploadDir = "file-storage";

    public String store(MultipartFile file) {
        try {
            String parentDir = UUID.randomUUID().toString();

            File folder = new File(uploadDir + "/" + parentDir);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String filename = file.getOriginalFilename();
            Path path = Paths.get(uploadDir + "/" + parentDir + "/" + filename);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return parentDir + "/" + filename;
        }
        catch(IOException e) {
                e.printStackTrace();
                return null;
        }
    }

    public String load(String path) {
        try {
            Path file = Paths.get(uploadDir + "/" + path);
            String content = new String(Files.readAllBytes(file));
            return content;
        } catch(IOException e) {
            return null;
        }
    }

    public void delete(String path) {
        try {
            Path file = Paths.get(uploadDir + "/" + path);
            Files.deleteIfExists(file);
        }catch (IOException e) {}
    }
}
