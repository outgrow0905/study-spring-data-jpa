package com.study.jpa.ch3.v1.repository;

import com.study.jpa.ch3.v1.entity.MyMemberV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyMemberV1Repository extends JpaRepository<MyMemberV1, Long> {
}
