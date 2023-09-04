package com.study.jpa.ch4.v3.repository;

import com.study.jpa.ch4.v3.entity.BookV3;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepositoryV3 extends JpaRepository<BookV3, Long> {
}
