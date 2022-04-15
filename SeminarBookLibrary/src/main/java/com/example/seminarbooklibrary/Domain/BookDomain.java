package com.example.seminarbooklibrary.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="books")
public class BookDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long idBook;
    @Column(name = "book_title")
    private String titleBook;
    @Column(name="book_author")
    private String authorBook;
    @Column(name="book_status")
    private int statusBook;
    @Column(name="book_img")
    private String imgBook;
}
