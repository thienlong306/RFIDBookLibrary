package com.example.seminarbooklibrary.Service;

import com.example.seminarbooklibrary.Domain.BookDomain;
import com.example.seminarbooklibrary.Domain.BorrowDomain;
import com.example.seminarbooklibrary.Model.BorrowModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public interface BorrowService {

    List<BorrowDomain> findAll();

    List<BorrowDomain> findAllById(Iterable<Long> longs);

    <S extends BorrowDomain> List<S> saveAll(Iterable<S> entities);

    <S extends BorrowDomain> S saveAndFlush(S entity);

    BorrowDomain getById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    BorrowDomain findTopByOrderByIdBorrowDesc();

    ArrayList<BorrowModel> getListBorrowModel();

    ArrayList<BorrowModel> getListUserBorrowModel(Long idUser);

    ArrayList<BorrowModel> getListBorrowModelByBorrower(String nameUser, Date dateFrom, Date dateTo);
}
