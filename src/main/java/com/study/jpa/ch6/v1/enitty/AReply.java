package com.study.jpa.ch6.v1.enitty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class AReply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "REPLY_NO")
    private Long id;

    private String text;

    @JoinColumn(name = "BOARD_NO")
    @ManyToOne
    private ABoard board;
}
