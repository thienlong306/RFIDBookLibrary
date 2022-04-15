package com.example.seminarbooklibrary.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class UserDomain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long idUser;
    @Column(name = "user_name")
    private String nameUser;
    @Column(name = "user_phone")
    private String phoneUser;
    @Column(name = "user_loginname")
    private String loginNameUser;
    @Column(name = "user_loginpassword")
    private String loginPasswordUser;
}
