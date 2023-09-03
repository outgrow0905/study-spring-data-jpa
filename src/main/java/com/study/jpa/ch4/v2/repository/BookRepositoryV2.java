package com.study.jpa.ch4.v2.repository;

import com.study.jpa.ch4.v2.entity.BookV2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepositoryV2 extends JpaRepository<BookV2, Long> {
}
