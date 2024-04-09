package com.guen.program.shop.repository.order;

import com.guen.program.shop.model.dto.request.ReqOrderSearchDto;
import com.guen.program.shop.model.entity.Order;
import com.guen.program.shop.model.entity.QCrew;
import com.guen.program.shop.model.entity.QOrder;
import com.guen.program.shop.model.enumclass.OrderStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;

public class OrderRepoExtendImpl extends QuerydslRepositorySupport implements OrderRepoExtend {

    private EntityManager em;
    private JPAQueryFactory jpaQueryFactory;
    public OrderRepoExtendImpl() {
        super(Order.class);
    }

    @Autowired
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
        super.setEntityManager(entityManager);
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public List<Order> findJpaQueryFactoryAll(ReqOrderSearchDto orderSearch) {
        List<Order> orders = jpaQueryFactory.selectFrom(QOrder.order)
                .join(QOrder.order.crew, QCrew.crew)
                .where(getPredicateCondition(orderSearch))
                .fetch();
        return orders;
    }

    public BooleanBuilder getPredicateCondition(ReqOrderSearchDto orderSearch){
        BooleanBuilder predicate = new BooleanBuilder();
        OrderStatus status = orderSearch.getOrderStatus();
        if(orderSearch.getCrewId()!=0){
            predicate.and(QOrder.order.crew.id.eq(orderSearch.getCrewId()));
        }
        if(status != null){
            predicate.and(QOrder.order.status.eq(orderSearch.getOrderStatus()));
        }
        return predicate;
    }

    /**
     * JPA Criteria
     */
    public List<Order> findAllByCriteria(ReqOrderSearchDto orderSearch) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("crew", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 아이디 검색
        if (orderSearch.getCrewId()!=0) {
            Predicate id =
                    cb.equal(m.<Long>get("id"), orderSearch.getCrewId());
            criteria.add(id);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }
}
