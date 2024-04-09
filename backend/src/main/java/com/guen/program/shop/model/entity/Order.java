package com.guen.program.shop.model.entity;

import com.guen.program.qdslstudy.model.entity.Employee;
import com.guen.program.shop.model.enumclass.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew_id", referencedColumnName = "id", nullable = true)
    private Crew crew;

    @OneToOne(fetch = FetchType.LAZY)
    private Delivery delivery;

    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;



    @Builder
    public Order(LocalDate orderDate, OrderStatus status) {
        this.orderDate = orderDate;
        this.status = status;
    }

    public void addCrew(Crew crew) {
        this.crew = crew;
    }

    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void cancel(){
        this.status = OrderStatus.CANCEL;
    }

    public static Order createOrder(final LocalDate orderDate, final OrderStatus status, final Crew crew, final Delivery delivery) {
        Order order = Order.builder()
                .orderDate(orderDate)
                .status(status)
                .build();
        order.addCrew(crew);
        order.addDelivery(delivery);
        return order;
    }
}
