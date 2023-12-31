package com.study.jpa.ch4.v1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Entity
@Getter
@Setter
@Slf4j
@NoArgsConstructor
public class ETeamV1 {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @OneToMany(mappedBy = "team")
    private List<EMemberV1> members;

    public ETeamV1(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ETeamV1(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ETeamV1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
