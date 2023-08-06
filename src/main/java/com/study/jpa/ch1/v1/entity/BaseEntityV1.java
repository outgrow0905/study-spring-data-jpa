package com.study.jpa.ch1.v1.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntityV1 {
    private LocalDateTime createdTime;
    private LocalDateTime lasModifiedTime;
}
