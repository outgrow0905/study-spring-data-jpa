package com.study.jpa.ch5.v2.repository;

import com.study.jpa.ch5.v2.enitty.GMemberV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GMemberRepositoryV1 extends JpaRepository<GMemberV1, Long> {
    @Query("select m from GMemberV1 m")
    List<GMemberV1> findMembers();
}
