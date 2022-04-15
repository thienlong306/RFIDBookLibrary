package com.example.seminarbooklibrary.Model;

import com.example.seminarbooklibrary.Domain.BookDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagReadModel {
    private String idTagRead;
    private Long idBook;
    private Date timeTagRead;
    private BookDomain bookDomain=null;
    private int status=1;
}
