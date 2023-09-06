package com.study.jpa.ch5.v1.repository;

import com.study.jpa.ch5.v1.enitty.FMemberV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FMemberRepositoryV1 extends JpaRepository<FMemberV1, Long> {
    @Query("select m from GMemberV1 m")
    List<FMemberV1> findMembers();
}
