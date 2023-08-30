package com.study.jpa.ch1.v1.repository;

import com.study.jpa.ch1.v1.entity.BookV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepositoryV1 extends JpaRepository<BookV1, Long> {
}
