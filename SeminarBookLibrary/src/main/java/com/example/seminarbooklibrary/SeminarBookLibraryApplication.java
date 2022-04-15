package com.example.seminarbooklibrary;

import com.example.seminarbooklibrary.Service.StorageService;
import com.example.seminarbooklibrary.config.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SeminarBookLibraryApplication{

    public static void main(String[] args) {
        SpringApplication.run(SeminarBookLibraryApplication.class, args);
    }

}
