package com.jwt.jwtTest.repo;

import com.jwt.jwtTest.domain.Userr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Userr, Long> {
    Userr findByUsername(String username);
}