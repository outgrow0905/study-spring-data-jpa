package com.study.jpa.ch4.v1.service;

import com.study.jpa.ch4.v1.entity.EComputerV1;
import com.study.jpa.ch4.v1.repository.EComputerRepositoryV1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ProxyTest {
    @Autowired
    private EComputerServiceV1 eComputerServiceV1;

    @Autowired
    private EComputerRepositoryV1 eComputerRepositoryV1;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    void compareProxy1() {
        // save
        EComputerV1 computer1 = new EComputerV1();
        computer1.setName("computer name1");
        eComputerRepositoryV1.save(computer1);

        // clear persistence context
        entityManager.clear();

        // compare
        EComputerV1 referenceComputer = eComputerRepositoryV1.getReferenceById(computer1.getId());
        EComputerV1 findComputer = eComputerRepositoryV1.findById(computer1.getId()).get();
        log.info("referenceComputer class: {}", referenceComputer.getClass()); // EComputerV1$HibernateProxy$Op3s5H31
        log.info("findComputer class: {}", findComputer.getClass()); // EComputerV1$HibernateProxy$Op3s5H31
    }

    @Test
    @Transactional
    void compareProxy2() {
        // save
        EComputerV1 computer1 = new EComputerV1();
        computer1.setName("computer name1");
        eComputerRepositoryV1.save(computer1);

        // clear persistence context
        entityManager.clear();

        // compare
        EComputerV1 findComputer = eComputerRepositoryV1.findById(computer1.getId()).get();
        EComputerV1 referenceComputer = eComputerRepositoryV1.getReferenceById(computer1.getId());
        log.info("findComputer class: {}", findComputer.getClass()); // class com.study.jpa.ch4.v1.entity.EComputerV1
        log.info("referenceComputer class: {}", referenceComputer.getClass()); // class com.study.jpa.ch4.v1.entity.EComputerV1
    }

    @Test
    void compareProxy3() {
        // save
        EComputerV1 computer1 = new EComputerV1();
        computer1.setName("computer name1");
        eComputerRepositoryV1.save(computer1);

        // clear persistence context
        entityManager.clear();

        // compare
        EComputerV1 referenceComputer = eComputerRepositoryV1.getReferenceById(computer1.getId());

        assertNotSame(EComputerV1.class, referenceComputer.getClass()); // not same
        assertTrue(referenceComputer instanceof EComputerV1);
    }
}