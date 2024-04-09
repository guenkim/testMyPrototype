package com.guen.program.shop.repository.orderitem;

import com.guen.program.shop.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepo extends JpaRepository<OrderItem,Long> ,OrderItemRepoExtend , JpaSpecificationExecutor<OrderItem> , QuerydslPredicateExecutor<OrderItem> {
    Optional<List<OrderItem>> findByOrderId(Long id);

}
