package com.study.jpa.ch5.v3;

import com.study.jpa.ch5.v3.enitty.GMemberV2;
import com.study.jpa.ch5.v3.enitty.GOrderV2;
import com.study.jpa.ch5.v3.repository.GMemberRepositoryV2;
import com.study.jpa.ch5.v3.repository.GOrderRepositoryV2;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest
@Rollback(value = false)
class Problem4Test {
    @Autowired
    private GMemberRepositoryV2 gMemberRepositoryV2;

    @Autowired
    private GOrderRepositoryV2 gOrderRepositoryV2;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void beforeEach() {
//        for (int i = 0; i < 100; i++) {
//            // save member
//            GMemberV2 member1 = new GMemberV2();
//            gMemberRepositoryV2.save(member1);
//
//            // save order
//            GOrderV2 order1 = new GOrderV2();
//            order1.setMember(member1);
//            gOrderRepositoryV2.save(order1);
//            GOrderV2 order2 = new GOrderV2();
//            order2.setMember(member1);
//            gOrderRepositoryV2.save(order2);
//        }

//        entityManager.flush(); // db 반영
//        entityManager.clear(); // 영속성 컨텍스트 clear
    }

    @Test
    @Transactional
    void problem1() {
        List<GMemberV2> members = gMemberRepositoryV2.findMembers();
        log.info("members: {}", members);

        for (GMemberV2 member : members) {
            List<GOrderV2> orders = member.getOrders();
            log.info("size: {}", orders.size());
        }
    }
}