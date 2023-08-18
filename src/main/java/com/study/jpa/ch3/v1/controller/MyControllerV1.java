package com.study.jpa.ch3.v1.controller;

import com.study.jpa.ch3.v1.entity.MyScheduleV1;
import com.study.jpa.ch3.v1.service.MyServiceV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyControllerV1 {

    private final MyServiceV1 myServiceV1;

    @GetMapping("schedule/{scheduleNo}")
    public void getSchedule(@PathVariable("scheduleNo") Long scheduleNo) {
        MyScheduleV1 schedule = myServiceV1.getSchedule(scheduleNo);
        log.info("member name: {}", schedule.getMember().getName());
    }
}
