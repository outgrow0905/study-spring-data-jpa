package com.study.jpa.ch5.v1;

import com.study.jpa.ch5.v1.enitty.FMemberV1;
import com.study.jpa.ch5.v1.enitty.FOrderV1;
import com.study.jpa.ch5.v1.repository.FMemberRepositoryV1;
import com.study.jpa.ch5.v1.repository.FOrderRepositoryV1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class Problem1Test {
    @Autowired
    private FMemberRepositoryV1 fMemberRepositoryV1;

    @Autowired
    private FOrderRepositoryV1 fOrderRepositoryV1;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void beforeEach() {
        // save member
        FMemberV1 member1 = new FMemberV1();
        fMemberRepositoryV1.save(member1);

        // save order
        FOrderV1 order1 = new FOrderV1();
        order1.setMember(member1);
        fOrderRepositoryV1.save(order1);
        FOrderV1 order2 = new FOrderV1();
        order2.setMember(member1);
        fOrderRepositoryV1.save(order2);

        // save member
        FMemberV1 member2 = new FMemberV1();
        fMemberRepositoryV1.save(member2);

        // save order
        FOrderV1 order3 = new FOrderV1();
        order3.setMember(member2);
        fOrderRepositoryV1.save(order3);
        FOrderV1 order4 = new FOrderV1();
        order4.setMember(member2);
        fOrderRepositoryV1.save(order4);
    }

    @AfterEach
    void afterEach() {
        log.info("================================");
        fOrderRepositoryV1.deleteAll();
        fMemberRepositoryV1.deleteAll();
    }

    @Test
    void problem1() {
        List<FMemberV1> members = fMemberRepositoryV1.findMembers();
    }
}