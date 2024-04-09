package com.guen.program.qdslstudy.model.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TestDto2 {
    private Short id;
    private BigDecimal salary;
    private String departmentName;

    public TestDto2(Short id, BigDecimal salary) {
        this.id = id;
        this.salary = salary;
    }

    public TestDto2(Short id, String departmentName) {
        this.id = id;
        this.departmentName = departmentName;
    }

    public TestDto2(Short id, BigDecimal salary , String departmentName) {
        this.id = id;
        this.salary = salary;
        this.departmentName = departmentName;
    }
}
