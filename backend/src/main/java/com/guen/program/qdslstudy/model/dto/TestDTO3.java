package com.guen.program.qdslstudy.model.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TestDTO3 {
    private String id;
    private BigInteger total;

    public TestDTO3(String id, BigInteger total) {
        this.id = id;
        this.total = total;
    }
}
