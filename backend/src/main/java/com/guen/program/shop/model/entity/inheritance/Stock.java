package com.guen.program.shop.model.entity.inheritance;

import com.guen.program.shop.model.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("S")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends Item {
    @Column(length = 100)
    private String ticker;

    public void editTicker(String ticker) {
        this.ticker = ticker;
    }

    public Stock(String ticker) {
        this.ticker = ticker;
    }

    @Builder
    public Stock(String name, int price, int stockQuanitty, String ticker) {
        super(name, price, stockQuanitty);
        this.ticker = ticker;
    }
}
