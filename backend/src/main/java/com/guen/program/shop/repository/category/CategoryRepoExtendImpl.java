package com.guen.program.shop.repository.category;

import com.guen.program.shop.model.entity.Category;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CategoryRepoExtendImpl extends QuerydslRepositorySupport implements CategoryRepoExtend {
    private JPAQueryFactory jpaQueryFactory;
    public CategoryRepoExtendImpl() {
        super(Category.class);
    }

    @Autowired
    @Override
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }
}
