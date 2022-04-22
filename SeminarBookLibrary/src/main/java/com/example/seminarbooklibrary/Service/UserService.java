package com.example.seminarbooklibrary.Service;

import com.example.seminarbooklibrary.Domain.UserDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserDomain> findAll();

    List<UserDomain> findAllById(Iterable<Long> longs);

    <S extends UserDomain> List<S> saveAll(Iterable<S> entities);

    <S extends UserDomain> S saveAndFlush(S entity);

    UserDomain getById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    UserDomain findByLoginNameUserAndLoginPasswordUser(String loginNameUser, String loginPasswordUser);
}
