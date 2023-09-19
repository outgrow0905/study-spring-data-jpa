package com.study.jpa.ch6.v1.repository;

import com.study.jpa.ch6.v1.enitty.ABoard;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ABoardRepositoryV1 extends JpaRepository<ABoard, Long> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    ABoard findABoardById(Long id);
}
