package com.example.seminarbooklibrary.Model;

import com.example.seminarbooklibrary.Domain.UserDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowModel {
    private Long idBorrow;
    private Date beginDateBorrow;
    private Date endDateBorrow;
    private Long idUser;
    private int statusBorrow;
    private Date returnDateBorrow;
    private UserDomain userDomain;
}
