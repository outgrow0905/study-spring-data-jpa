package com.study.jpa.ch4.v2.repository;

import com.study.jpa.ch4.v2.entity.OrderItemV2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepositoryV2 extends JpaRepository<OrderItemV2, Long> {
}
