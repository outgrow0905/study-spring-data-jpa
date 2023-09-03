package com.study.jpa.ch4.v2.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("A")
public class AlbumV2 extends ItemV2 {
    private String artist;
    private String etc;

    @Override
    public String getView() {
        return "AlbumV2{" +
                "artist='" + artist + '\'' +
                ", etc='" + etc + '\'' +
                '}';
    }
}
