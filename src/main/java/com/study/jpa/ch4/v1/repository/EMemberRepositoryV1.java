package com.study.jpa.ch4.v1.repository;

import com.study.jpa.ch4.v1.entity.EMemberV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EMemberRepositoryV1 extends JpaRepository<EMemberV1, Long> {
}
