package com.study.jpa.ch4.v2.entity;

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
public class OrderItemV2 extends BaseEntityV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @JoinColumn(name = "ITEM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemV2 item;

    @JoinColumn(name = "ORDER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderV2 order;

    private int orderPrice;

    private int count;

    public OrderItemV2(ItemV2 item, int count) {
        this.item = item;
        this.count = count;
        this.orderPrice = item.getPrice() * count;
    }

    public void printTitleView() {
        log.info("view: {}", item.getView());
    }
}
