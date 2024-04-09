package com.guen.program.shop.repository.order;

import com.guen.program.shop.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> ,OrderRepoExtend , JpaSpecificationExecutor<Order> , QuerydslPredicateExecutor<Order> {

    @Query("select o from Order o inner join o.crew c inner join o.delivery where c.id = :crewId")
    List<Order> findOrderByCrewId(@Param("crewId")long crewId);






}
