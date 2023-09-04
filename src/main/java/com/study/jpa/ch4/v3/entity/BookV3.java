package com.study.jpa.ch4.v3.entity;

import com.study.jpa.ch4.v3.visitor.Visitor;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("B")
public class BookV3 extends ItemV3 {
    private String author;
    private String isbn;

    @Override
    public String getView() {
        return "BookV3{" +
                "author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
