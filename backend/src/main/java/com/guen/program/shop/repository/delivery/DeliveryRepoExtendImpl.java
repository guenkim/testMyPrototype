package com.guen.program.shop.repository.delivery;

import com.guen.program.shop.model.entity.Delivery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class DeliveryRepoExtendImpl extends QuerydslRepositorySupport implements DeliveryRepoExtend{

    private JPAQueryFactory queryFactory;

    public DeliveryRepoExtendImpl() {
        super(Delivery.class);
    }

    @Autowired
    @Override
    public void setEntityManager(EntityManager em){
        super.setEntityManager(em);
        queryFactory = new JPAQueryFactory(em);
    }

}
