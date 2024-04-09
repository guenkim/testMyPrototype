package com.guen.program.apitest.model.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TestReq {
    private Buyer buyer;
    private List<Item> items = new ArrayList<>();
    private List<Coupon> coupons = new ArrayList<>();
    private List<Delivery> deliveries = new ArrayList<>();
    private LocalDate buyDate;

    @JsonCreator
    public TestReq(
            @JsonProperty("buyer") Buyer buyer,
            @JsonProperty("items") List<Item> items,
            @JsonProperty("coupons") List<Coupon> coupons,
            @JsonProperty("deliveries") List<Delivery> deliveries,
            @JsonProperty("buyDate") LocalDate buyDate
    ) {
        this.buyer = buyer;
        this.items = items;
        this.coupons = coupons;
        this.deliveries = deliveries;
        this.buyDate = buyDate;
    }


    public static class Buyer{
        private String id;

        @JsonCreator
        public Buyer(@JsonProperty("id") String id) {
            this.id = id;
        }
    }

    public static class Item{

        private String id;

        private int count;

        private Delivery delivery;

        @JsonCreator
        public Item(
                @JsonProperty("id") String id,
                @JsonProperty("count") int count,
                @JsonProperty("delivery") Delivery delivery
        ) {
            this.id = id;
            this.count = count;
            this.delivery = delivery;
        }

        public static class Delivery{

            private String address;
            @JsonCreator
            public Delivery(
                    @JsonProperty("address") String address
            ) {
                this.address = address;
            }
        }

    }

    public static class Coupon{
        private String id;
        private int count;

        @JsonCreator
        public Coupon(
                @JsonProperty("id")  String id,
                @JsonProperty("count")  int count
        ) {
            this.id = id;
            this.count = count;
        }
    }

    public static class Delivery{

        private String address;
        private String priority;

        @JsonCreator
        public Delivery(
                @JsonProperty("address") String address,
                @JsonProperty("priority") String priority
        ) {
            this.address = address;
            this.priority = priority;
        }
    }
}
