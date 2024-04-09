package com.guen.program.shop.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="crew")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Crew {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name", length = 50)
    private String name;

    @Embedded
    private Address address;


    @Builder
    public Crew(String name, Address address) {
        this.name = name;
        this.address = address;
    }

}