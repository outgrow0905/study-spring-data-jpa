package com.study.jpa.v1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class OrderV1 extends BaseEntityV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @JoinColumn(name="USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserV1 user;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemV1> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_ID")
    DeliveryV1 delivery;

    public void setUser(UserV1 user) {
        if (this.user != null) {
            this.user.getOrders().remove(this);
        }

        this.user = user;
        this.user.getOrders().add(this);
    }

    public void addOrderItem(OrderItemV1 orderItem) {
        orderItem.setOrder(this);
        orderItems.add(orderItem);
    }

    public void setDelivery(DeliveryV1 delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public enum OrderStatus {
        ORDER, CANCEL
    }
}