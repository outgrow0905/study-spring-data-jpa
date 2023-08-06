package com.study.jpa.ch1.v2;

import com.study.jpa.ch1.v1.entity.UserV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryV2 extends JpaRepository<UserV1, Long> {
}
