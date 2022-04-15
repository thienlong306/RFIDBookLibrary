package com.example.seminarbooklibrary.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long idUser;
    private String nameUser;
    private String phoneUser;
    private String loginNameUser;
    private String loginPasswordUser;
}
