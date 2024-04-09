package com.guen.program.shop.model.dto.response;

import com.guen.program.shop.model.entity.Address;
import com.guen.program.shop.model.enumclass.DeliveryStatus;
import com.guen.program.shop.model.enumclass.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "주문 정보")
@Getter
public class OrderDto {

    @Schema(description = "주문자 정보")
    private Crew crew;

    @Schema(description = "배송정보")
    private List<Order> order;

    @Schema(description = "총 주문 가격")
    private BigInteger totalOrderPrice;

    public OrderDto(Crew crew, List<Order> order,BigInteger totalOrderPrice) {
        this.crew = crew;
        this.order = order;
        this.totalOrderPrice=totalOrderPrice;
    }


    @Schema(description = "주문자 정보")
    @Getter
    public static class Crew {
        @Schema(description = "주문자 아이디",  example = "주문자 아이디")
        private Long id;
        @Schema(description = "주문자", example = "주문자")
        private String name;
        public Crew(Long id,String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Schema(description = "주문 물품 정보")
    @Getter
    public static class Order {

        @Schema(description = "주문 아이디", example = "주문 아이디")
        private Long id;

        @Schema(description = "주문 시간",  example = "yyyyMMdd")
        private LocalDate orderDate;

        @Schema(description = "주문상태", example = "[ORDER, CANCEL]")
        private OrderStatus status; //주문상태 [ORDER, CANCEL]

        @Schema(description = "배송정보")
        private Delivery delivery;

        @Schema(description = "주문 물품 정보")
        private List<OrderItem> orderItems = new ArrayList<>();


        public Order(Long id, Crew crew, LocalDate orderDate, OrderStatus status, Delivery delivery, List<OrderItem> orderItems) {
            this.id = id;
            this.orderDate = orderDate;
            this.status = status;
            this.delivery = delivery;
            this.orderItems = orderItems;
        }


        @Schema(description = "배송지 정보")
        @Getter
        public static class Delivery {
            @Schema(description = "배송지 주소")
            private Address address;
            @Schema(description = "배송 상태 정보", example = "READY, COMP")
            private DeliveryStatus status; //READY, COMP

            public Delivery(Address address, DeliveryStatus status) {
                this.address = address;
                this.status = status;
            }
        }

        @Schema(description = "주문 물품 정보")
        @Getter
        public static class OrderItem {

            @Schema(description = "주문 물품 정보")
            private Item item;
            @Schema(description = "주문 물품 가격")
            private int orderPrice; //주문 가격
            @Schema(description = "주문 물품 수량")
            private int count; //주문 수량
            @Schema(description = "주문 물품 총 가격")
            private BigInteger totalOrderPrice; //주문 물품 총 가격

            public OrderItem(Long id, Item item, int orderPrice, int count, BigInteger totalOrderPrice) {
                this.item = item;
                this.orderPrice = orderPrice;
                this.count = count;
                this.totalOrderPrice = totalOrderPrice;
            }

            @Schema(description = "주문 물품")
            @Getter
            public static class Item {
                @Schema(description = "주문 물품 아이디")
                private Long id;
                @Schema(description = "주문 물품명")
                private String name;
                @Schema(description = "주문 물품 가격")
                private int price;
                @Schema(description = "주문 물품 재고")
                private int stockQuantity;

                public Item(Long id, String name, int price, int stockQuantity) {
                    this.id = id;
                    this.name = name;
                    this.price = price;
                    this.stockQuantity = stockQuantity;
                }
            }
        }
    }
}