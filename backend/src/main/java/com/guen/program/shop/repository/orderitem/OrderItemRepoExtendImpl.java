package com.guen.program.shop.repository.orderitem;

import com.guen.program.shop.model.entity.OrderItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class OrderItemRepoExtendImpl extends QuerydslRepositorySupport  implements OrderItemRepoExtend {

    private JPAQueryFactory queryFactory;

    public OrderItemRepoExtendImpl(){
        super(OrderItem.class);
    }

    @Override
    @Autowired
    public void setEntityManager(EntityManager em){
        super.setEntityManager(em);
        queryFactory = new JPAQueryFactory(em);
    }
}
