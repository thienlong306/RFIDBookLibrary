package com.example.seminarbooklibrary.Repository;

import com.example.seminarbooklibrary.Domain.TagReadDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagReadRepository extends JpaRepository<TagReadDomain,String> {
    TagReadDomain findByIdBook(Long idBook);
}
