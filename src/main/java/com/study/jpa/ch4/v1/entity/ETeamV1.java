package com.study.jpa.ch4.v1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ETeamV1 {
    @Id
    private Long id;

    private String name;

    @ManyToOne
    private EMemberV1 member;
}
