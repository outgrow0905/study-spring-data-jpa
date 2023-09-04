package com.study.jpa.ch4.v3.repository;

import com.study.jpa.ch4.v3.entity.OrderItemV3;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepositoryV3 extends JpaRepository<OrderItemV3, Long> {
}
