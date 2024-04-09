package com.guen.program.shop.repository;

import com.guen.program.shop.model.entity.*;
import com.guen.program.shop.model.entity.inheritance.Coin;
import com.guen.program.shop.model.entity.inheritance.Item;
import com.guen.program.shop.model.entity.inheritance.Stock;
import com.guen.program.shop.model.enumclass.DeliveryStatus;
import com.guen.program.shop.model.enumclass.OrderStatus;
import com.guen.program.shop.repository.category.CategoryRepo;
import com.guen.program.shop.repository.crew.CrewRepo;
import com.guen.program.shop.repository.delivery.DeliveryRepo;
import com.guen.program.shop.repository.item.ItemRepo;
import com.guen.program.shop.repository.order.OrderRepo;
import com.guen.program.shop.repository.orderitem.OrderItemRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class OrderRepoTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    CrewRepo crewRepo;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    DeliveryRepo deliveryRepo;

    @Autowired
    ItemRepo itemRepo;

    @Autowired
    OrderItemRepo orderItemRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Test
    public void test() {

        // given
        Address address = Address.builder()
                .city("seoul")
                .street("mangwon")
                .zipcode("12345")
                .build();

        Crew crew = Crew.builder()
                .name("geun")
                .address(address)
                .build();

        crewRepo.save(crew);

        Delivery delivery = Delivery.builder()
                .address(address)
                .status(DeliveryStatus.READY)
                .build();

        deliveryRepo.save(delivery);


        Stock stock = Stock.builder().ticker("bidu")
                .name("baidu")
                .price(10000000)
                .stockQuanitty(1000000000)
                .build();

        Stock stock1 = Stock.builder().ticker("grab")
                .name("grab")
                .price(10000000)
                .stockQuanitty(1000000000)
                .build();

        Coin coin = Coin.builder().company("이더리엄")
                .name("이더리엄")
                .price(10000000)
                .stockQuanitty(1000000000)
                .build();


        itemRepo.save(stock);
        itemRepo.save(stock1);
        itemRepo.save(coin);


        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .status(OrderStatus.ORDER)
                .build();
        order.addCrew(crew);
        order.addDelivery(delivery);
        orderRepo.save(order);


        itemRepo.findById(1L).ifPresentOrElse(
                it -> {
                    OrderItem orderItem1 = OrderItem.builder()
                            .orderPrice(10000)
                            .count(2)
                            .build();

                    orderItem1.addOrderItem(order, it);
                    orderItemRepo.save(orderItem1);
                },
                () -> {
                    Coin coin2 = Coin.builder().company("이더리엄")
                            .name("이더리엄")
                            .price(10000000)
                            .stockQuanitty(1000000000)
                            .build();
                    itemRepo.save(coin2);

                    OrderItem orderItem1 = OrderItem.builder()
                            .orderPrice(10000)
                            .count(2)
                            .build();

                    orderItem1.addOrderItem(order, coin2);
                    orderItemRepo.save(orderItem1);

                }
        );

        itemRepo.findById(3L).ifPresentOrElse(
                it -> {
                    OrderItem orderItem1 = OrderItem.builder()
                            .orderPrice(10000)
                            .count(2)
                            .build();

                    orderItem1.addOrderItem(order, it);
                    orderItemRepo.save(orderItem1);
                },
                () -> {
                    Coin coin2 = Coin.builder().company("이더리엄")
                            .name("이더리엄")
                            .price(10000000)
                            .stockQuanitty(1000000000)
                            .build();
                    itemRepo.save(coin2);

                    OrderItem orderItem1 = OrderItem.builder()
                            .orderPrice(10000)
                            .count(2)
                            .build();

                    orderItem1.addOrderItem(order, coin2);
                    orderItemRepo.save(orderItem1);

                }
        );

        //when
        orderItemRepo.findByOrderId(order.getId()).ifPresent(orderItems -> {
            orderItems.forEach(
                    orderItem -> {
                        if (orderItem.getItem() instanceof Stock) {
                            System.out.println("stock");
                            System.out.println(orderItem.getItem().getName());
                        } else {
                            System.out.println("coin");
                            System.out.println(orderItem.getItem().getName());
                        }
                    }
            );
        });
        //then
    }

    @Test
    @Rollback(value = false)
    public void 계층구조_테스트() {
        //given
        Category finance = Category.builder()
                .name("stock")
                .build();

        Stock stock = Stock.builder()
                .stockQuanitty(100)
                .price(2000000)
                .name("바이두")
                .ticker("bidu")
                .build();

        List<Category> categories = new ArrayList<>();
        categories.add(finance);


        finance.addItem(stock);


        categoryRepo.save(finance);
        itemRepo.save(stock);

        em.flush();
        em.clear();

        //when
    }

    @Test
    @Rollback(value = false)
    public void 다대다관계테스트() throws Exception {
        //given
        Category finance = Category.builder()
                .name("finance")
                .build();

        Category book = Category.builder()
                .name("book")
                .build();


        Stock stockItem = Stock.builder()
                .stockQuanitty(100)
                .price(2000000)
                .name("바이두")
                .ticker("bidu")
                .build();

        Stock stockItem2 = Stock.builder()
                .stockQuanitty(100)
                .price(2000000)
                .name("그랩")
                .ticker("grab")
                .build();

        List<Category> categories = new ArrayList<>();
        categories.add(finance);
        categories.add(book);

        List<Item> items = new ArrayList<>();
        items.add(stockItem);
        items.add(stockItem2);

        finance.addItem(stockItem);
        finance.addItem(stockItem2);
        book.addItem(stockItem);
        book.addItem(stockItem2);


        categoryRepo.save(finance);
        categoryRepo.save(book);
        itemRepo.save(stockItem);
        itemRepo.save(stockItem2);

        em.flush();
        em.clear();

        //when
        categoryRepo.findById(finance.getId()).ifPresent(c->{
            c.getItems().forEach(item->{
                System.out.println(item.getName());
            });
        });
    }

}