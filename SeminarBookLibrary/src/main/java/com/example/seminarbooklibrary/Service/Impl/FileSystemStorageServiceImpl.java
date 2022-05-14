package com.example.seminarbooklibrary.Service.Impl;

import com.example.seminarbooklibrary.Service.StorageService;
import com.example.seminarbooklibrary.Config.StorageProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageServiceImpl implements StorageService {
    private final Path rootLocation;

    @Override
    public String getStroredFileName(MultipartFile file, String id){
        String ext= FilenameUtils.getExtension(file.getOriginalFilename());
        return "p" + id + "."+ext;
    }
    public FileSystemStorageServiceImpl(StorageProperties properties){
        this.rootLocation= Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file, String storedFilename){
        try {
            if (file.isEmpty()){
                System.out.println("Failed to store emtpy file");
            }
            Path destinationFile=this.rootLocation.resolve(Paths.get(storedFilename))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())){
                System.out.println("Cannot store file outside current directory");
            }
            try(InputStream inputStream=file.getInputStream()){
                Files.copy(inputStream,destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.out.println("Failed to store file");
        }
    }
    @Override
    public Resource loadAsResource(String filename){
        Resource resource=null;
        try {
            Path file=load(filename);
            resource=new UrlResource(file.toUri());
            if (resource.exists()||resource.isReadable()){
                return resource;
            }
            System.out.println("Could not read file"+filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return resource;
    }

    @Override
    public Path load(String filename){
        return rootLocation.resolve(filename);
    }

    @Override
    public void delete(String storedFilename){
        Path destinationFile=rootLocation.resolve(Paths.get(storedFilename))
                .normalize().toAbsolutePath();
        try {
            Files.delete(destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void init(){
        try {
            Files.createDirectories(rootLocation);
            System.out.println(rootLocation.toString());
        } catch (IOException e) {
            System.out.println("Could not initialize storage");
        }
    }
}