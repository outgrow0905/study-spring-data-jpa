package com.study.jpa.v1.repository;

import com.study.jpa.v1.entity.UserV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryV1 extends JpaRepository<UserV1, Long> {
}
