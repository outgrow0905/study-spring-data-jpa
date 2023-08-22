package com.study.jpa.ch4.v1.entity;

import com.study.jpa.ch4.v1.repository.EMemberRepositoryV1;
import com.study.jpa.ch4.v1.repository.ETeamRepositoryV1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ConverterTest {

    @Autowired
    private EMemberRepositoryV1 eMemberRepositoryV1;

    @Autowired
    private ETeamRepositoryV1 eTeamRepositoryV1;

    @Test
    void collection1() {
        ETeamV1 teamV1 = new ETeamV1(1L, "team name1");
        EMemberV1 member1 = new EMemberV1(1L, "member name1", true);

        eMemberRepositoryV1.save(member1);
        eTeamRepositoryV1.save(teamV1);


        eMemberRepositoryV1.deleteById(1L);
        eTeamRepositoryV1.deleteById(1L);

    }
}