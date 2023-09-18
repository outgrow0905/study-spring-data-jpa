package com.study.jpa.ch6.v1.repository;

import com.study.jpa.ch6.v1.enitty.HMemberV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HMemberRepositoryV1 extends JpaRepository<HMemberV1, Long> {
    @Query("select m from HMemberV1 m")
    List<HMemberV1> findMembers();
}
