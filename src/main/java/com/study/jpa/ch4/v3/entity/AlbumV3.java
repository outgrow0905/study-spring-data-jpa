package com.study.jpa.ch4.v3.entity;

import com.study.jpa.ch4.v3.visitor.Visitor;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("A")
public class AlbumV3 extends ItemV3 {
    private String artist;
    private String etc;

    @Override
    public String getView() {
        return "AlbumV3{" +
                "artist='" + artist + '\'' +
                ", etc='" + etc + '\'' +
                '}';
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
