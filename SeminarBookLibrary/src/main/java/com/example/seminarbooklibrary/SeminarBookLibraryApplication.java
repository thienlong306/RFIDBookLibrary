package com.example.seminarbooklibrary;

import com.example.seminarbooklibrary.Service.StorageService;
import com.example.seminarbooklibrary.Config.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SeminarBookLibraryApplication {

  public static void main(String[] args) {
    SpringApplication.run(SeminarBookLibraryApplication.class, args);
  }

  @Bean
  CommandLineRunner init(StorageService storageService) {
    return (
      args -> {
        storageService.init();
      }
    );
  }
}
