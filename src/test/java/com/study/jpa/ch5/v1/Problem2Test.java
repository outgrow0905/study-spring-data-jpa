package com.study.jpa.ch5.v1;

import com.study.jpa.ch5.v2.enitty.GMemberV1;
import com.study.jpa.ch5.v2.enitty.GOrderV1;
import com.study.jpa.ch5.v2.repository.GMemberRepositoryV1;
import com.study.jpa.ch5.v2.repository.GOrderRepositoryV1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest
class Problem2Test {
    @Autowired
    private GMemberRepositoryV1 gMemberRepositoryV1;

    @Autowired
    private GOrderRepositoryV1 gOrderRepositoryV1;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void beforeEach() {
        // save member
        GMemberV1 member1 = new GMemberV1();
        gMemberRepositoryV1.save(member1);

        // save order
        GOrderV1 order1 = new GOrderV1();
        order1.setMember(member1);
        gOrderRepositoryV1.save(order1);
        GOrderV1 order2 = new GOrderV1();
        order2.setMember(member1);
        gOrderRepositoryV1.save(order2);

        // save member
        GMemberV1 member2 = new GMemberV1();
        gMemberRepositoryV1.save(member2);

        // save order
        GOrderV1 order3 = new GOrderV1();
        order3.setMember(member2);
        gOrderRepositoryV1.save(order3);
        GOrderV1 order4 = new GOrderV1();
        order4.setMember(member2);
        gOrderRepositoryV1.save(order4);

        entityManager.flush(); // db 반영
        entityManager.clear(); // 영속성 컨텍스트 clear
    }

    @AfterEach
    void afterEach() {
        log.info("================================");
        gOrderRepositoryV1.deleteAll();
        gMemberRepositoryV1.deleteAll();
    }

    @Test
    @Transactional
    void problem1() {
        List<GMemberV1> members = gMemberRepositoryV1.findMembers();
        members.forEach(member -> {
            List<GOrderV1> orders = member.getOrders();
            log.info("orders size: {}", orders.size());
        });
    }
}