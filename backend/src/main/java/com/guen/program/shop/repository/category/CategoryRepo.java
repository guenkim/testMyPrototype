package com.guen.program.shop.repository.category;

import com.guen.program.shop.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Long> ,CategoryRepoExtend , JpaSpecificationExecutor<Category> , QuerydslPredicateExecutor<Category> {
    List<Category> findByIdIn(List<Long> ids);


}
