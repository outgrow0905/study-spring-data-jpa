package com.study.jpa.ch5.v3.repository;

import com.study.jpa.ch5.v3.enitty.GMemberV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GMemberRepositoryV2 extends JpaRepository<GMemberV2, Long> {
    @Query("select m from GMemberV2 m")
    List<GMemberV2> findMembers();
}
