package com.study.jpa.ch4.v1.service;

import com.study.jpa.ch4.v1.entity.ETeamV1;
import com.study.jpa.ch4.v1.repository.ETeamRepositoryV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
@Slf4j
public class ETeamServiceV1 {
    private final ETeamRepositoryV1 eTeamRepositoryV1;

//    @Transactional
    public Long saveTeam(ETeamV1 team) {
        log.info("tx name1: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        return eTeamRepositoryV1.save(team).getId();
    }
}
