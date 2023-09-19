package com.study.jpa.ch6.v1.enitty;

import com.study.jpa.ch6.v1.repository.HMemberRepositoryV1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Rollback(value = false)
//@Transactional(propagation = Propagation.REQUIRES_NEW)
class LockV1Test {
    @Autowired
    private HMemberRepositoryV1 hMemberRepositoryV1;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    void lock1() throws Exception {
        HMemberV1 member = hMemberRepositoryV1.findById(1L).get();
        log.info("thread: {}, member: {}", Thread.currentThread().getName(), member);
        member.setMoney(member.getMoney() - 5);

        // update on query browser
        Thread.sleep(5000);
    }

    @Test
    @Transactional(isolation = Isolation.READ_COMMITTED)
    void lock2() throws Exception {
        HMemberV1 member = hMemberRepositoryV1.findById(1L).get();
        log.info("member: {}", member);

        // update on query browser
        Thread.sleep(10000);

        // clear
        entityManager.flush();
        entityManager.clear();

        member = hMemberRepositoryV1.findById(1L).get();
        log.info("member: {}", member);
    }
}