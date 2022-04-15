package com.example.seminarbooklibrary.Repository;

import com.example.seminarbooklibrary.Domain.BorrowDetailDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowDetailRepository extends JpaRepository<BorrowDetailDomain,Long> {
    List<BorrowDetailDomain> findAllByIdBorrow(Long idBorrow);
}
