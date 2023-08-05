package com.study.jpa.v1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrderItemV1 extends BaseEntityV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @JoinColumn(name = "ITEM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemV1 item;

    @JoinColumn(name = "ORDER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderV1 order;

    private int orderPrice;

    private int count;

    public OrderItemV1(ItemV1 item, int count) {
        this.item = item;
        this.count = count;
        this.orderPrice = item.getPrice() * count;
    }
}
