package com.guen.program.qdslstudy.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "COUNTRIES", schema = "HR")
public class Country {
    @Id
    @Size(max = 2)
    @Column(name = "COUNTRY_ID", nullable = false, length = 2, columnDefinition = "char(2)")
    private String countryId;

    @Size(max = 40)
    @Column(name = "COUNTRY_NAME", length = 40)
    private String countryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID")
    private Region region;

    @OneToMany(mappedBy = "country")
    private Set<Location> locations = new LinkedHashSet<>();

}