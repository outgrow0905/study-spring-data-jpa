package com.study.jpa.v1.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("B")
public class BookV1 extends ItemV1 {
    private String author;
    private String isbn;
}
