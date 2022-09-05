package com.akata.clientservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    @Value("${upload.dir}")
    String upload_dir;


    public String saveImage(MultipartFile file) throws IOException {
        String file_name = StringUtils.cleanPath(file.getOriginalFilename());
        Path upload_path = Paths.get(upload_dir);
        if(!Files.exists(upload_path)){
            Files.createDirectories(upload_path);
        }
        try (InputStream inputStream = file.getInputStream()){
            Path file_path = upload_path.resolve(file_name);
            Files.copy(inputStream, file_path, StandardCopyOption.REPLACE_EXISTING);
            return file_name;
        }catch (IOException e){
            throw new IOException("Could not save image file: " + file_name + e);
        }
    }

    public Resource loadFile(String file_name) throws IOException {
        Path upload_path = Paths.get(upload_dir);
        Path file_path = upload_path.resolve(file_name).normalize();
        Resource resource = new UrlResource(file_path.toUri());
        if(resource.exists()){
            return resource;
        }else {
            throw new IOException("Resource not found");
        }
    }
}
