package com.guen.program.apitest.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "main_entity")
public class MainEntity {

    @EmbeddedId
    private CompositeKey compositeKey;

    @OneToMany(mappedBy = "mainEntity")
    private List<SubEntity> anotherEntities;

}
