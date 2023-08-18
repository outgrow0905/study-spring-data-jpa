package com.study.jpa.ch3.v1.repository;

import com.study.jpa.ch3.v1.entity.MyScheduleV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyScheduleV1Repository extends JpaRepository<MyScheduleV1, Long> {
}
