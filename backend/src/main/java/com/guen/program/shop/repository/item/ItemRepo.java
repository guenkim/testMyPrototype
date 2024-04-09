package com.guen.program.shop.repository.item;

import com.guen.program.shop.model.entity.inheritance.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ItemRepo extends JpaRepository<Item,Long> ,ItemRepoExtend , JpaSpecificationExecutor<Item> , QuerydslPredicateExecutor<Item> {
}
