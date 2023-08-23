package com.study.jpa.ch4.v1.listener;

import com.study.jpa.ch4.v1.entity.EComputerV1;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComputerV1Listener {
    
    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(EComputerV1 computer) {
        if (computer.getId() == null) {
            log.info("[AUDIT] About to add a computer");
        } else {
            log.info("[AUDIT] About to update/delete computer: " + computer.getId());
        }
    }
    
    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(EComputerV1 computer) {
        log.info("[AUDIT] add/update/delete complete for computer: " + computer.getId());
    }
    
    @PostLoad
    private void afterLoad(EComputerV1 computer) {
        log.info("[AUDIT] computer loaded from database: " + computer.getId());
    }
}