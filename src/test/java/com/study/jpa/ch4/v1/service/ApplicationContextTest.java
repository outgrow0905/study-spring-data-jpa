package com.study.jpa.ch4.v1.service;

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
    private ETeamServiceV1 eTeamServiceV1;
    @Autowired
    private ETeamRepositoryV1 eTeamRepositoryV1;

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
}