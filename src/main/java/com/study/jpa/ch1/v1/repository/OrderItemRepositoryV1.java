package com.study.jpa.ch1.v1.repository;

import com.study.jpa.ch1.v1.entity.OrderItemV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepositoryV1 extends JpaRepository<OrderItemV1, Long> {
}
