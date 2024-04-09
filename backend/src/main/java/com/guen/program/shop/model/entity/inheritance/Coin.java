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
@DiscriminatorValue("C")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coin extends Item{
    @Column(length = 100)
    private String company;

    public void editCompany(String company) {
        this.company = company;
    }

    public Coin(String company) {
        this.company = company;
    }

    @Builder
    public Coin(String name, int price, int stockQuanitty, String company) {
        super(name, price, stockQuanitty);
        this.company = company;
    }
}
