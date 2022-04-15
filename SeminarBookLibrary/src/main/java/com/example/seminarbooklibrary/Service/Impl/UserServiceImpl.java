package com.example.seminarbooklibrary.Service.Impl;

import com.example.seminarbooklibrary.Domain.UserDomain;
import com.example.seminarbooklibrary.Repository.UserRepository;
import com.example.seminarbooklibrary.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDomain> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDomain> findAllById(Iterable<Long> longs) {
        return userRepository.findAllById(longs);
    }

    @Override
    public <S extends UserDomain> List<S> saveAll(Iterable<S> entities) {
        return userRepository.saveAll(entities);
    }

    @Override
    public <S extends UserDomain> S saveAndFlush(S entity) {
        return userRepository.saveAndFlush(entity);
    }

    @Override
    public UserDomain getById(Long aLong) {
        return userRepository.getById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return userRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return userRepository.count();
    }
}
