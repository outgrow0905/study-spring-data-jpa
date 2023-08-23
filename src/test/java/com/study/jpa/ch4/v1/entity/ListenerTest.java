package com.study.jpa.ch4.v1.entity;

import com.study.jpa.ch4.v1.repository.EComputerRepositoryV1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ListenerTest {

    @Autowired
    private EComputerRepositoryV1 eComputerRepositoryV1;

    @Test
    void listener1() {
        EComputerV1 computer = new EComputerV1();
        computer.setName("computer name1");
        eComputerRepositoryV1.save(computer);
    }

    @Test
    void listener2() {
        EComputerV1 computer = eComputerRepositoryV1.findById(12L).get();
        computer.setName("computer name2");
        eComputerRepositoryV1.save(computer);
    }
}