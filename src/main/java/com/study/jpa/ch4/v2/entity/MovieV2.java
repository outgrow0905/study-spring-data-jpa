package com.study.jpa.ch4.v2.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("M")
public class MovieV2 extends ItemV2 {
    private String director;
    private String actor;

    @Override
    public String getView() {
        return "MovieV2{" +
                "director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                '}';
    }
}
