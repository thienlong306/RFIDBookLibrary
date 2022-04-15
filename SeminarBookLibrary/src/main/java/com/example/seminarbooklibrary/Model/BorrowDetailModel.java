package com.example.seminarbooklibrary.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowDetailModel {
    private Long idBorrowDetail;
    private Long idBook;
    private Long idBorrow;
}
