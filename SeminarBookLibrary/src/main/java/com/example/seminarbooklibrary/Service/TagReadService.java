package com.example.seminarbooklibrary.Service;

import com.example.seminarbooklibrary.Domain.TagReadDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagReadService {

    List<TagReadDomain> findAll();

    List<TagReadDomain> findAllById(Iterable<String> strings);

    <S extends TagReadDomain> List<S> saveAll(Iterable<S> entities);

    TagReadDomain findByIdBook(Long idBook);

    void deleteById(String s);

    <S extends TagReadDomain> S saveAndFlush(S entity);

    TagReadDomain getById(String s);

    boolean existsById(String s);

    long count();
}
