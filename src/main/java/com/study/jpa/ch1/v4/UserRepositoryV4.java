package com.study.jpa.ch1.v4;

import com.study.jpa.ch1.v1.entity.UserV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryV4 extends JpaRepository<UserV1, Long>, CustomizedUserRepository {
}
