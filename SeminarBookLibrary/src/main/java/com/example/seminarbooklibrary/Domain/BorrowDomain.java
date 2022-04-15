package com.example.seminarbooklibrary.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="borrow")
public class BorrowDomain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrow_id")
    private Long idBorrow;
    @Temporal(TemporalType.DATE)
    @Column(name = "borrow_begindate")
    private Date beginDateBorrow;
    @Temporal(TemporalType.DATE)
    @Column(name = "borrow_enddate")
    private Date endDateBorrow;
    @Column(name = "user_id")
    private Long idUser;
    @Column(name = "borrrow_status")
    private int statusBorrow;
    @Temporal(TemporalType.DATE)
    @Column(name = "borrow_returndate")
    private Date returnDateBorrow;
}
