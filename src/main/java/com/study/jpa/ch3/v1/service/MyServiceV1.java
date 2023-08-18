package com.study.jpa.ch3.v1.service;

import com.study.jpa.ch3.v1.entity.MyScheduleV1;
import com.study.jpa.ch3.v1.repository.MyScheduleV1Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyServiceV1 {

    private final MyScheduleV1Repository myScheduleV1Repository;

    @Transactional
    public MyScheduleV1 getSchedule(Long scheduleNo) {
        MyScheduleV1 schedule = myScheduleV1Repository.findById(scheduleNo).get();
        return schedule;
    }
}
