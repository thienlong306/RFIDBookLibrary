package com.example.seminarbooklibrary.Service.Impl;

import com.example.seminarbooklibrary.Domain.BorrowDetailDomain;
import com.example.seminarbooklibrary.Repository.BorrowDetailRepository;
import com.example.seminarbooklibrary.Service.BorrowDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowDetailServiceImpl implements BorrowDetailService {
    @Autowired
    BorrowDetailRepository borrowDetailRepository;

    @Override
    public List<BorrowDetailDomain> findAll() {
        return borrowDetailRepository.findAll();
    }

    @Override
    public List<BorrowDetailDomain> findAllById(Iterable<Long> longs) {
        return borrowDetailRepository.findAllById(longs);
    }

    @Override
    public List<BorrowDetailDomain> findAllByIdBorrow(Long idBorrow) {
        return borrowDetailRepository.findAllByIdBorrow(idBorrow);
    }

    @Override
    public <S extends BorrowDetailDomain> List<S> saveAll(Iterable<S> entities) {
        return borrowDetailRepository.saveAll(entities);
    }

    @Override
    public <S extends BorrowDetailDomain> S saveAndFlush(S entity) {
        return borrowDetailRepository.saveAndFlush(entity);
    }

    @Override
    public BorrowDetailDomain getById(Long aLong) {
        return borrowDetailRepository.getById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return borrowDetailRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return borrowDetailRepository.count();
    }
}
