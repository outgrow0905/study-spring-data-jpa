package com.study.jpa.ch4.v1.entity;

import com.study.jpa.ch4.v1.listener.ComputerV1Listener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Setter
@Slf4j
@EntityListeners(ComputerV1Listener.class)
public class EComputerV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Override
    public String toString() {
        return "EComputerV1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


    @PostLoad
    public void postLoad() {
        log.info("postLoad: {}", this);
    }

    @PrePersist
    public void prePersist() {
        log.info("prePersist: {}", this);
    }

    @PostPersist
    public void postPersist() {
        log.info("postPersist: {}", this);
    }

    @PreUpdate
    public void preUpdate() {
        log.info("preUpdate: {}", this);
    }

    @PostUpdate
    public void postUpdate() {
        log.info("postUpdate: {}", this);
    }

    @PreRemove
    public void preRemove() {
        log.info("preRemove: {}", this);
    }

    @PostRemove
    public void postRemove() {
        log.info("postRemove: {}", this);
    }
}
