package com.guen.program.shop.service;

import com.guen.program.shop.exception.ItemNotFindException;
import com.guen.program.shop.exception.NotMemberException;
import com.guen.program.shop.exception.OrderNotFindException;
import com.guen.program.shop.model.dto.request.ReqOrderDto;
import com.guen.program.shop.model.dto.request.ReqOrderSearchDto;
import com.guen.program.shop.model.dto.response.OrderDto;
import com.guen.program.shop.model.entity.*;
import com.guen.program.shop.model.entity.inheritance.Item;
import com.guen.program.shop.model.enumclass.DeliveryStatus;
import com.guen.program.shop.model.enumclass.OrderStatus;
import com.guen.program.shop.repository.crew.CrewRepo;
import com.guen.program.shop.repository.delivery.DeliveryRepo;
import com.guen.program.shop.repository.item.ItemRepo;
import com.guen.program.shop.repository.order.OrderRepo;
import com.guen.program.shop.repository.orderitem.OrderItemRepo;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional(readOnly = true)
public class OrderService {


    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CrewRepo crewRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private DeliveryRepo deliveryRepo;

    @Transactional
    public void createOrder(final Long crewId, final Long itemId, final int count) throws Exception {
        Crew crew = crewRepo.findById(crewId).orElseThrow(() -> new Exception("그러한 회원이 없어!"));

        Delivery delivery = Delivery.builder()
                .address(crew.getAddress())
                .status(DeliveryStatus.READY)
                .build();
        deliveryRepo.save(delivery);

        Order order = Order.createOrder(LocalDate.now(), OrderStatus.ORDER, crew, delivery);
        orderRepo.save(order);

        Item item = itemRepo.findById(itemId).orElseThrow(() -> new ItemNotFindException("그러한 아이템이 없어!"));
        item.reduceStock(count);
        itemRepo.save(item);

        OrderItem orderItem = OrderItem.createOrderItem(item.getPrice(), count, order, item);
        orderItemRepo.save(orderItem);
    }

    @Transactional
    public void createMultipleOrder(final ReqOrderDto reqOrderDto) throws Exception {
        Crew crew = crewRepo.findById(Long.valueOf(reqOrderDto.getCrewId())).orElseThrow(() -> new Exception("그러한 회원이 없어!"));

        Delivery delivery = Delivery.builder()
                .address(crew.getAddress())
                .status(DeliveryStatus.READY)
                .build();
        deliveryRepo.save(delivery);

        Order order = Order.createOrder(LocalDate.now(), OrderStatus.ORDER, crew, delivery);
        orderRepo.save(order);

        reqOrderDto.getOrders().forEach(
                orderInfo -> {
                    Item item = itemRepo.findById(Long.valueOf(orderInfo.getItemId())).orElseThrow(() -> new ItemNotFindException("그러한 아이템이 없어!"));
                    item.reduceStock(orderInfo.getCount());
                    itemRepo.save(item);

                    OrderItem orderItem = OrderItem.createOrderItem(item.getPrice(), orderInfo.getCount(), order, item);
                    orderItemRepo.save(orderItem);
                }
        );


    }

    //검색
    public OrderDto findOrders(final ReqOrderSearchDto orderSearch) throws NotMemberException {
        List<OrderDto.Order> retOrder = new ArrayList<>();
        Crew crew1 = crewRepo.findById(orderSearch.getCrewId()).orElseThrow(() -> new NotMemberException("그러한 회원이 없어!"));
        OrderDto.Crew crew = new OrderDto.Crew(crew1.getId(), crew1.getName());
        AtomicReference<BigInteger> totalOrderPrice = new AtomicReference<>(BigInteger.ZERO);
        
        //JpaSpecificationExecutor 테스트
        //orderRepo.findAll(getSpecficationCondition(orderSearch)).forEach(

        //QuerydslPredicateExecutor 테스트
        //orderRepo.findAll(getPredicateCondition(orderSearch)).forEach(

        //JpaQueryFactory 테스트
        orderRepo.findJpaQueryFactoryAll(orderSearch).forEach(

        //CriteriaBuilder 테스트
        //orderRepo.findAllByCriteria(orderSearch).forEach(
                order -> {
                    OrderDto.Order.Delivery delivery = new OrderDto.Order.Delivery(order.getDelivery().getAddress(), order.getDelivery().getStatus());
                    List<OrderItem> retOrderItem = orderItemRepo.findByOrderId(order.getId()).orElseThrow(()->new OrderNotFindException("그런 order 없어!"));
                    List<OrderDto.Order.OrderItem> orderItems = new ArrayList<>();
                    retOrderItem.forEach(orderItem -> {
                        OrderDto.Order.OrderItem orderItem1 = new OrderDto.Order.OrderItem(
                                orderItem.getId(),
                                new OrderDto.Order.OrderItem.Item(orderItem.getItem().getId(), orderItem.getItem().getName(), orderItem.getItem().getPrice(), orderItem.getItem().getStockQuanitty()),
                                orderItem.getOrderPrice(),
                                orderItem.getCount(),
                                orderItem.getTotalPrice()
                        );
                        orderItems.add(orderItem1);
                        totalOrderPrice.updateAndGet(currentValue -> currentValue.add(orderItem1.getTotalOrderPrice()));
                    });

                    OrderDto.Order orderResponse = new OrderDto.Order(order.getId(), crew, order.getOrderDate(), order.getStatus(), delivery, orderItems);
                    retOrder.add(orderResponse);
                }
        );

        OrderDto orderDto = new OrderDto(crew, retOrder, totalOrderPrice.get());
        return orderDto;
    }

    @Transactional
    public void cancel(final Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFindException("그런 주문 없어"));
        order.cancel();
        orderRepo.save(order);

        List<OrderItem> orderItems = orderItemRepo.findByOrderId(order.getId()).orElseThrow(() -> new OrderNotFindException("그런 주문 없어"));
        orderItems.forEach(orderItem -> {
            int cancelCnt = orderItem.getCount();
            Item item = orderItem.getItem();
            item.addStock(cancelCnt);
            itemRepo.save(item);
        });
    }


    public Specification<Order> getSpecficationCondition(ReqOrderSearchDto orderSearch){
        Specification<Order> spec = Specification.where(null);
        OrderStatus status = orderSearch.getOrderStatus();
        if(orderSearch.getCrewId()!=0){
            spec = spec.and((root, query, criteriaBuilder) -> {
                root.join("crew", JoinType.INNER); // Inner Join
                return criteriaBuilder.equal(root.get("crew").get("id"),orderSearch.getCrewId());
            });
        }
        if(status != null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), orderSearch.getOrderStatus()));
        }
        return spec;
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


}
