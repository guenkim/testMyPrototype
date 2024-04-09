package com.guen.program.qdslstudy.model.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
public class CustomerDTO {
    private int customerId;
    private String firstNm;
    private String decodeGender;
    private String caseGender;

   public CustomerDTO(int customerId, String firstNm, String decodeGender, String caseGender) {
        this.customerId = customerId;
        this.firstNm = firstNm;
        this.decodeGender = decodeGender;
        this.caseGender = caseGender;
    }
}
