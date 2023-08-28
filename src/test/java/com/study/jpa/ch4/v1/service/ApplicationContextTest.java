package com.study.jpa.ch4.v1.service;

import com.study.jpa.ch4.v1.entity.EComputerV1;
import com.study.jpa.ch4.v1.entity.ETeamV1;
import com.study.jpa.ch4.v1.repository.EComputerRepositoryV1;
import com.study.jpa.ch4.v1.repository.ETeamRepositoryV1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

@Slf4j
@SpringBootTest
class ApplicationContextTest {

    @Autowired
    private ETeamRepositoryV1 eTeamRepositoryV1;

    @Autowired
    private EComputerServiceV1 eComputerServiceV1;

    @Autowired
    private EComputerRepositoryV1 eComputerRepositoryV1;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    void compareEntity1() {
        // given
        ETeamV1 team1 = new ETeamV1(1L, "team name1");
        ETeamV1 merge1 = entityManager.merge(team1);

        assertNotSame(merge1, team1);
    }

    @Test
    @Transactional
    void compareEntity2() {
        // given
        ETeamV1 team1 = new ETeamV1(1L, "team name1");

        // when
        entityManager.persist(team1);

        // then
        ETeamV1 findTeam = eTeamRepositoryV1.findById(1L).get();
        assertSame(team1, findTeam);
    }

    // 테스트를 수행하려면 ETeamV1테이블을 drop하고 @GeneratedValue(strategy = GenerationType.IDENTITY) 속성을 추가해야 한다.
    @Test
    @Transactional
    void compareEntity3() {
//        // given
//        ETeamV1 team1 = new ETeamV1( "team name1"); // id null
//
//        // when
//        ETeamV1 savedTeam = eTeamRepositoryV1.save(team1); // spring jpa의 save() 메서드를 사용한다.
//
//        // then
//        ETeamV1 findTeam = eTeamRepositoryV1.findById(savedTeam.getId()).get();
//        assertSame(team1, findTeam); // same
    }

    @Test
    @Transactional
    void compareEntity4() {
        // given
        EComputerV1 computer1 = new EComputerV1();
        computer1.setName("computer name1");

        // when
        eComputerServiceV1.saveComputer(computer1);

        // then
        EComputerV1 findComputer = eComputerRepositoryV1.findById(computer1.getId()).get();
        assertSame(computer1, findComputer); // same
    }

    @Test
    void compareEntity5() {
        // given
        EComputerV1 computer1 = new EComputerV1();
        computer1.setName("computer name1");

        // when
        eComputerServiceV1.saveComputer(computer1);

        // then
        EComputerV1 findComputer = eComputerRepositoryV1.findById(computer1.getId()).get();
        assertNotSame(computer1, findComputer); // not same
    }
}