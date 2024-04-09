package com.guen.common.file.repository;

import com.guen.common.file.model.entity.Files;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class FileJpaExtendImpl extends QuerydslRepositorySupport implements FileJpaExtend {

    private JPAQueryFactory jpaQueryFactory;

    @Override
    @Autowired
    public void setEntityManager(EntityManager entityManager){
        super.setEntityManager(entityManager);
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public FileJpaExtendImpl() {
        super(Files.class);
    }
}
