package com.study.jpa.ch5.v2.enitty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class GMemberV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_NO")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<GOrderV1> orders;
}
