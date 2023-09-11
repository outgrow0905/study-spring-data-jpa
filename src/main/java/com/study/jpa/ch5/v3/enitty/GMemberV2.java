package com.study.jpa.ch5.v3.enitty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GMemberV2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_NO")
    private Long id;

    private String name;

    private String address;

    @OneToMany(mappedBy = "member")
    @BatchSize(size = 30)
    private List<GOrderV2> orders;

    public GMemberV2(Long id) {
        this.id = id;
    }
}
