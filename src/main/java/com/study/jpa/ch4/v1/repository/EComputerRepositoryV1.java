package com.study.jpa.ch4.v1.repository;

import com.study.jpa.ch4.v1.entity.EComputerV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EComputerRepositoryV1 extends JpaRepository<EComputerV1, Long> {
}
