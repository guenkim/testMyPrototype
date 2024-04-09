package com.guen.program.qdslstudy.model.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TestDTO4 {
    private int id;
    private String deptNm;
    private int deptSal;
    private BigDecimal divide;
    private String salary_level;
    private String salary_info;

    public TestDTO4(int id, String deptNm, int deptSal, BigDecimal divide, String salary_level, String salary_info) {
        this.id = id;
        this.deptNm = deptNm;
        this.deptSal = deptSal;
        this.divide = divide;
        this.salary_level = salary_level;
        this.salary_info = salary_info;
    }
}
