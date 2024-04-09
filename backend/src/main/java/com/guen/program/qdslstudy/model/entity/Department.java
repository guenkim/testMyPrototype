package com.guen.program.qdslstudy.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "DEPARTMENTS", schema = "HR")
public class Department {
    @Id
    @Column(name = "DEPARTMENT_ID", nullable = false)
    private Short id;

    @Size(max = 30)
    @NotNull
    @Column(name = "DEPARTMENT_NAME", nullable = false, length = 30)
    private String departmentName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @OneToMany(mappedBy = "department")
    private Set<Employee> employees = new LinkedHashSet<>();

    @OneToMany(mappedBy = "department")
    private Set<JobHistory> jobHistories = new LinkedHashSet<>();

}