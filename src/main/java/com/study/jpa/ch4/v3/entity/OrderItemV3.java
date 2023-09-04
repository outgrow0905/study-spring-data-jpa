package com.study.jpa.ch4.v3.entity;

import com.study.jpa.ch1.v1.entity.BaseEntityV1;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Slf4j
public class OrderItemV3 extends BaseEntityV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @JoinColumn(name = "ITEM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemV3 item;

    @JoinColumn(name = "ORDER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderV3 order;

    private int orderPrice;

    private int count;

    public OrderItemV3(ItemV3 item, int count) {
        this.item = item;
        this.count = count;
        this.orderPrice = item.getPrice() * count;
    }

    public void printTitleView() {
        log.info("view: {}", item.getView());
    }
}
