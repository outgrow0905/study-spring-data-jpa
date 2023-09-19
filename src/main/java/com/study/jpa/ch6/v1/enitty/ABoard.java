package com.study.jpa.ch6.v1.enitty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class ABoard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOARD_NO")
    private Long id;

    private String title;

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
    List<AReply> replies;

    @Version
    private Integer version;
}
