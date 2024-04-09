package com.guen.program.shop.repository.order;

import com.guen.program.shop.model.dto.request.ReqOrderSearchDto;
import com.guen.program.shop.model.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepoExtend {

    List<Order> findAllByCriteria(ReqOrderSearchDto orderSearch);
    List<Order> findJpaQueryFactoryAll(ReqOrderSearchDto orderSearch);
}
