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
@Table(name = "borrow_detail")
public class BorrowDetailDomain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrowdetail_id")
    private Long idBorrowDetail;
    @Column(name = "book_id")
    private Long idBook;
    @Column(name = "borrow_id")
    private Long idBorrow;
}
