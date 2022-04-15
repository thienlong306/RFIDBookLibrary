package com.example.seminarbooklibrary.Repository;

import com.example.seminarbooklibrary.Domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDomain,Long> {
}
