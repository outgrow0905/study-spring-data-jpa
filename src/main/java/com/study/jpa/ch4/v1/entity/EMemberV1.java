package com.study.jpa.ch4.v1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class EMemberV1 {
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "member")
    Set<ETeamV1> teams = new HashSet<>();

    @OneToMany(mappedBy = "member")
    List<ETeamV1> teamList = new ArrayList<>();

    public EMemberV1(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
