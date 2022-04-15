package com.example.seminarbooklibrary.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookModel {
    private String idTagRead;
    private Long idBook;
    private String titleBook;
    private String authorBook;
    private int statusBook;
    private String imgBook;
    private String descriptionBook;
    private MultipartFile imgFile;
}
