package com.example.seminarbooklibrary.Repository;

import com.example.seminarbooklibrary.Domain.BorrowDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowDomain,Long> {
    BorrowDomain findTopByOrderByIdBorrowDesc();
    List<BorrowDomain> findAllByIdUser(Long idUser);
}
