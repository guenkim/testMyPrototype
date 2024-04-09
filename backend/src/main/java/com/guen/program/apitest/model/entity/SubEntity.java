package com.guen.program.apitest.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "subEntity")
public class SubEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "id1", referencedColumnName = "id1"),
            @JoinColumn(name = "id2", referencedColumnName = "id2")
    } , foreignKey = @ForeignKey(name = "FK_SUB_MAIN")
    )
    private MainEntity mainEntity;

}