package com.guen.program.shop.model.entity;

import com.guen.program.shop.model.entity.inheritance.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name="ordersitem")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20)
    private int orderPrice;

    @Column(length = 10)
    private int count;

    @Builder
    public OrderItem(int orderPrice, int count) {
        this.orderPrice = orderPrice;
        this.count = count;
    }

    @ManyToOne
    @JoinColumn(name = "order_id" , referencedColumnName = "id" , nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name="item_id" , referencedColumnName = "id" , nullable = false)
    private Item item;

    public void addOrderItem(Order order,Item item){
        this.order = order;
        this.item = item;
    }

    public static OrderItem  createOrderItem(final int orderPrice, final int count , final Order order, final Item item){
        OrderItem orderItem = OrderItem.builder()
                .orderPrice(orderPrice)
                .count(count)
                .build();
        orderItem.addOrderItem(order,item);
        return orderItem;
    }

    public BigInteger getTotalPrice(){
        return BigInteger.valueOf(getOrderPrice()).multiply(BigInteger.valueOf(getCount()));
    }
}
