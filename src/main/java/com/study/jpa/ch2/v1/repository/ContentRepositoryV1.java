package com.study.jpa.ch2.v1.repository;

import com.study.jpa.ch2.v1.entity.ContentV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepositoryV1 extends JpaRepository<ContentV1, Long>, ContentRepositoryCustomV1 {
}
