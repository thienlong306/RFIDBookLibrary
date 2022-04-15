package com.example.seminarbooklibrary.Service.Impl;

import com.example.seminarbooklibrary.Domain.TagReadDomain;
import com.example.seminarbooklibrary.Repository.TagReadRepository;
import com.example.seminarbooklibrary.Service.TagReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagReadServiceImpl implements TagReadService {
    @Autowired
    TagReadRepository tagReadRepository;

    @Override
    public List<TagReadDomain> findAll() {
        return tagReadRepository.findAll();
    }

    @Override
    public List<TagReadDomain> findAllById(Iterable<String> strings) {
        return tagReadRepository.findAllById(strings);
    }

    @Override
    public <S extends TagReadDomain> List<S> saveAll(Iterable<S> entities) {
        return tagReadRepository.saveAll(entities);
    }

    @Override
    public TagReadDomain findByIdBook(Long idBook) {
        return tagReadRepository.findByIdBook(idBook);
    }

    @Override
    public void deleteById(String s) {
        tagReadRepository.deleteById(s);
    }

    @Override
    public <S extends TagReadDomain> S saveAndFlush(S entity) {
        return tagReadRepository.saveAndFlush(entity);
    }

    @Override
    public TagReadDomain getById(String s) {
        return tagReadRepository.getById(s);
    }

    @Override
    public boolean existsById(String s) {
        return tagReadRepository.existsById(s);
    }

    @Override
    public long count() {
        return tagReadRepository.count();
    }
}
