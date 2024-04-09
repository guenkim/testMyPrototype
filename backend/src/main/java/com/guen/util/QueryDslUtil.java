package com.guen.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.guen.program.todo.model.entity.QTodo.todo;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public class QueryDslUtil {

    public static OrderSpecifier<?> getSortedColumn(Order order, Path<?> parent, String fieldName) {
        Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);
        return new OrderSpecifier(order, fieldPath);
    }

    public static List<OrderSpecifier> getDBAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier> ORDERS = new ArrayList<>();
        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "id":
                        OrderSpecifier<?> orderId = QueryDslUtil.getSortedColumn(direction, todo, "id");
                        ORDERS.add(orderId);
                        break;
                    case "subject":
                        OrderSpecifier<?> orderSubject = QueryDslUtil.getSortedColumn(direction, todo, "subject");
                        ORDERS.add(orderSubject);
                        break;
                    default:
                        break;
                }
            }
        }
        return ORDERS;
    }

    public static Pageable getDBSortPage(Pageable pageable) {
        List<Sort.Order> sort = new ArrayList<>();
        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                String fieldName="";
                Sort.Direction direction = order.getDirection().isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC;
                switch (order.getProperty()) {
                    case "id":
                        fieldName = "id";
                        break;
                    case "subject":
                        fieldName = "subject";
                        break;
                    default:
                        break;
                }
                sort.add(Sort.Order.by(fieldName).with(direction));
            }
        }
        return org.springframework.data.domain.PageRequest.of(pageable.getPageNumber(), pageable.getPageSize() ,Sort.by(sort));
    }


}