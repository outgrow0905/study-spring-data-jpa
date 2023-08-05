package com.study.jpa.v1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class UserV1 extends BaseEntityV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;
    private String name;
    private AddressV1 address;

    @OneToMany(mappedBy = "user")
    List<OrderV1> orders = new ArrayList<>();
}
