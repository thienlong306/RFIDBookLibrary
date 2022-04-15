package com.example.seminarbooklibrary.Service;

import com.example.seminarbooklibrary.Domain.BorrowDetailDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowDetailService {
    List<BorrowDetailDomain> findAll();

    List<BorrowDetailDomain> findAllById(Iterable<Long> longs);

    List<BorrowDetailDomain> findAllByIdBorrow(Long idBorrow);

    <S extends BorrowDetailDomain> List<S> saveAll(Iterable<S> entities);

    <S extends BorrowDetailDomain> S saveAndFlush(S entity);

    BorrowDetailDomain getById(Long aLong);

    boolean existsById(Long aLong);

    long count();
}
