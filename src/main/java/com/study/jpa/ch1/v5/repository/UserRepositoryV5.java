package com.study.jpa.ch1.v5.repository;

import com.study.jpa.ch1.v5.entity.UserV2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryV5 extends JpaRepository<UserV2, Long> {
}
