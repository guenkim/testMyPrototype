package com.guen.program.shop.model.entity.inheritance;


import com.guen.program.shop.exception.NotEnoughStockException;
import com.guen.program.shop.model.entity.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
public abstract class Item {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100 , nullable = false)
    private String name;
    @Column(length = 10 , nullable = false)
    private int price;
    @Column(length = 10 , nullable = false)
    private int stockQuanitty;

    public void editName(String name) {
        this.name = name;
    }

    public void editPrice(int price) {
        this.price = price;
    }

    public void editStockQuanitty(int stockQuanitty) {
        this.stockQuanitty = stockQuanitty;
    }

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public Item(String name, int price, int stockQuanitty ) {
        this.name = name;
        this.price = price;
        this.stockQuanitty = stockQuanitty;
    }

    public void addStock(int quantity){
        this.stockQuanitty += quantity;
    }

    public void reduceStock(int quantity){
        int restStock = this.stockQuanitty - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuanitty = restStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
