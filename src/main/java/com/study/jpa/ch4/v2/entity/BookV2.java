package com.study.jpa.ch4.v2.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("B")
public class BookV2 extends ItemV2 {
    private String author;
    private String isbn;

    @Override
    public String getView() {
        return "BookV2{" +
                "author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
