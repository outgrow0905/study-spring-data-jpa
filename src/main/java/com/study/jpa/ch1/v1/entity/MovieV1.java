package com.study.jpa.ch1.v1.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("M")
public class MovieV1 extends ItemV1 {
    private String director;
    private String actor;
}
