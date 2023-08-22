package com.study.jpa.ch4.v1.entity;

import com.study.jpa.ch4.v1.converter.BooleanToYnConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class EMemberV1 {
    @Id
    private Long id;

    private String name;

    @Convert(converter = BooleanToYnConverter.class)
    private boolean vip;

    @ManyToOne
    private ETeamV1 team;

    public EMemberV1(Long id, String name, boolean vip) {
        this.id = id;
        this.name = name;
        this.vip = vip;
    }
}
