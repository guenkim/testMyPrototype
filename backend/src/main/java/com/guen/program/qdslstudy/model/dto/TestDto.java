package com.guen.program.qdslstudy.model.dto;


import com.querydsl.core.types.dsl.Expressions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TestDto {
    private int employee_id;
    private String names;
    private BigDecimal salary;
    private Double avgs;

    public TestDto(int employee_id, String names, BigDecimal salary, Double avgs, BigDecimal maxs) {
        this.employee_id = employee_id;
        this.names = names;
        this.salary = salary;
        this.avgs = avgs;
        this.maxs = maxs;
    }

    private BigDecimal maxs;

}
