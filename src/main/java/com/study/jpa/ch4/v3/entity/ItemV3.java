package com.study.jpa.ch4.v3.entity;

import com.study.jpa.ch4.v2.entity.TitleView;
import com.study.jpa.ch4.v3.visitor.Visitor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ItemV3 implements TitleView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

//    @ManyToMany(mappedBy = "items")
//    private List<CategoryV1> categories;

    public abstract void accept(Visitor visitor);
}
