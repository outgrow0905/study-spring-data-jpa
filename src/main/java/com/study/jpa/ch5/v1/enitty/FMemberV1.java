package com.study.jpa.ch5.v1.enitty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class FMemberV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_NO")
    private Long id;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private List<FOrderV1> orders;
}
