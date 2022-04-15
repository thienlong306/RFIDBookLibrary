package com.example.seminarbooklibrary.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    String getStroredFileName(MultipartFile file, String id);

    void store(MultipartFile file, String storedFilename);

    Resource loadAsResource(String filename);

    Path load(String filename);

    void delete(String storedFilename);

    void init();
}
