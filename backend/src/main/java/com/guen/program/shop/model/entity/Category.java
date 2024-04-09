package com.guen.program.shop.model.entity;


import com.guen.program.shop.model.entity.inheritance.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Entity
@Table(name="category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 100 , nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY)
    private List<Category> child = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    public void addChildCategory(Category child){
        this.child.add(child);
        updateParent(child);
    }

    public void updateParent(Category child){
        child.parent = this;

    }
    @Builder
    public Category(String name) {
        this.name = name;
    }

    public void addItem(Item item){
        Iterator<Item> iterator = items.iterator();
        boolean itemMatch = false;
        while (iterator.hasNext()) {
            Item it = iterator.next();
            if(it.equals(item)){
                itemMatch = true;
            }
        }
        if(itemMatch == false) this.items.add(item);
    }
    public void removeItem(Item item){
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item it = iterator.next();
            if(it.equals(item)){
                iterator.remove();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
