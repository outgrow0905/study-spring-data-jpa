package com.study.jpa.ch6.v1.repository;

import com.study.jpa.ch6.v1.enitty.AReply;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface AReplyRepositoryV1 extends JpaRepository<AReply, Long> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    List<AReply> findRepliesByBoardId(Long id);
}
