package com.guen.program.shop.repository.item;

import com.guen.program.shop.model.entity.inheritance.Item;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ItemRepoExtendImpl extends QuerydslRepositorySupport  implements ItemRepoExtend {

    private JPAQueryFactory queryFactory;

    public ItemRepoExtendImpl(){
        super(Item.class);
    }

    @Autowired
    @Override
    public void setEntityManager(EntityManager em){
        super.setEntityManager(em);
        queryFactory = new JPAQueryFactory(em);
    }
}
