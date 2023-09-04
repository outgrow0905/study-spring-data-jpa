package com.study.jpa.ch4.v3.entity;

import com.study.jpa.ch4.v3.visitor.Visitor;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("M")
public class MovieV3 extends ItemV3 {
    private String director;
    private String actor;

    @Override
    public String getView() {
        return "MovieV3{" +
                "director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                '}';
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
