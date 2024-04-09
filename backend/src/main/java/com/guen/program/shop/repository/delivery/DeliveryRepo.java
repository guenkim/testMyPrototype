package com.guen.program.shop.repository.delivery;

import com.guen.program.shop.model.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DeliveryRepo extends JpaRepository<Delivery,Long> , DeliveryRepoExtend , JpaSpecificationExecutor<Delivery> , QuerydslPredicateExecutor<Delivery> {

}
