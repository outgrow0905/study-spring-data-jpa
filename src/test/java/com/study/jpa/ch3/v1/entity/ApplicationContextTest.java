package com.study.jpa.ch3.v1.entity;

import com.study.jpa.ch3.v1.repository.MyMemberV1Repository;
import com.study.jpa.ch3.v1.repository.MyScheduleV1Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationContextTest {

    @Autowired
    private MyMemberV1Repository myMemberV1Repository;

    @Autowired
    private MyScheduleV1Repository myScheduleV1Repository;

    @Test
    void setData() {
        MyMemberV1 member = new MyMemberV1();
        MyScheduleV1 schedule = new MyScheduleV1();
        schedule.setMember(member);
        myMemberV1Repository.save(member);
        myScheduleV1Repository.save(schedule);
    }
}