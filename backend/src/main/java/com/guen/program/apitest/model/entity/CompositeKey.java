package com.guen.program.apitest.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CompositeKey implements Serializable {

    @Column(name = "id1")
    private String id1;

    @Column(name = "id2")
    private String id2;

}