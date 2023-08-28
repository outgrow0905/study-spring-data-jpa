package com.study.jpa.ch4.v1.service;

import com.study.jpa.ch4.v1.entity.EComputerV1;
import com.study.jpa.ch4.v1.repository.EComputerRepositoryV1;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EComputerServiceV1 {
    private final EComputerRepositoryV1 eComputerRepositoryV1;

    @Transactional
    public Long saveComputer(EComputerV1 computer) {
        return eComputerRepositoryV1.save(computer).getId();
    }
}
