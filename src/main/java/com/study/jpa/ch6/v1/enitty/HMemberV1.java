package com.study.jpa.ch6.v1.enitty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class HMemberV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_NO")
    private Long id;

    private String name;

    private Integer money;

    @Version
    private Integer version;
}
