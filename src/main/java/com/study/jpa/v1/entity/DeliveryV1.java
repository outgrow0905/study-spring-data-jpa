package com.study.jpa.v1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DeliveryV1 extends BaseEntityV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DELIVERY_ID")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    OrderV1 order;

    private AddressV1 address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public enum DeliveryStatus {
        READY, COMPLETE
    }
}
