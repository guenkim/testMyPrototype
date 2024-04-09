package com.guen.program.todo.model.puredto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AgreegationDto {

    private int id;
    private int total;

    public AgreegationDto(int id, int total) {
        this.id = id;
        this.total = total;
    }

    @Override
    public String toString() {
        return "AgreegationDto{" +
                "id=" + id +
                ", total=" + total +
                '}';
    }
}
