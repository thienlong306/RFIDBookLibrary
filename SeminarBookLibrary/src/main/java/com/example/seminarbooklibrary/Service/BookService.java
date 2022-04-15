package com.example.seminarbooklibrary.Service;

import com.caen.RFIDLibrary.CAENRFIDException;
import com.example.seminarbooklibrary.Domain.BookDomain;
import com.example.seminarbooklibrary.Model.TagReadModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface BookService {

    ArrayList<Long> getListIdBook();

    void setListIdBook(ArrayList<Long> listIdBook);

    void addBookBorrow(Long idbook);

    void deleteBookBorrow(Long idbook);

    void clearListIdBook();

    List<BookDomain> findAll();

    List<BookDomain> findAllById(Iterable<Long> longs);

    List<BookDomain> findByTitleBookContaining(String titleBook);

    List<BookDomain> findByAuthorBookContaining(String authorBook);


    BookDomain findTopByOrderByIdBookDesc();

    void deleteById(Long aLong);

    <S extends BookDomain> S saveAndFlush(S entity);

    <S extends BookDomain> List<S> saveAllAndFlush(Iterable<S> entities);

    BookDomain getById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    ArrayList<Long> getListIdBookByListRFIDTest();

    ArrayList<TagReadModel> getListInfoRfidTest();

    ArrayList<Long> getListIdBookByListRFID() throws Exception;

    ArrayList<TagReadModel> getListInfoRFID() throws Exception;
}
