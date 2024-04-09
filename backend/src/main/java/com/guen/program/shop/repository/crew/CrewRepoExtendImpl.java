package com.guen.program.shop.repository.crew;

import com.guen.program.shop.model.entity.Crew;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CrewRepoExtendImpl extends QuerydslRepositorySupport implements CrewRepoExtend{

    private JPAQueryFactory jpaQueryFactory;

    public CrewRepoExtendImpl() {
        super(Crew.class);
    }


    @Autowired
    @Override
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }


}
