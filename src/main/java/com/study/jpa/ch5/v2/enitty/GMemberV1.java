package com.study.jpa.ch5.v2.enitty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GMemberV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_NO")
    private Long id;

    private String name;

    private String address;

    @OneToMany(mappedBy = "member")
    private List<GOrderV1> orders;

    public GMemberV1(Long id) {
        this.id = id;
    }
}
