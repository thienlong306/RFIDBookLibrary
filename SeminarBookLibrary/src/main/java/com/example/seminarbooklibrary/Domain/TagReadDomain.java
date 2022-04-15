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
@Table(name="tag_read")
public class TagReadDomain implements Serializable {
    @Id
    @Column(name = "tag_rfid")
    private String idTagRead;
    @Column(name = "book_id")
    private Long idBook;
    @Temporal(TemporalType.DATE)
    @Column(name = "tag_time")
    private Date timeTagRead;
}
