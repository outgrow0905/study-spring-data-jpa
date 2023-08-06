package com.study.jpa.ch1.v1.repository;

import com.study.jpa.ch1.v1.entity.UserV1;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepositoryV1 extends JpaRepository<UserV1, Long> {
    List<UserV1> findByNameAndAddressCity(String name, String city, Pageable pageable);
    long countUserByName(String name);
    @Transactional
    void deleteUserByName(String name);
}
