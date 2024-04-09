package com.guen.program.shop.model.entity;

import com.guen.program.shop.model.enumclass.DeliveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "주소 정보")
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {

    @Schema(description = "도시")
    @Column(name="city" , length = 100)
    private String city;

    @Schema(description = "거리명")
    @Column(name="street" , length = 100)
    private String street;

    @Schema(description = "zipcode")
    @Column(name="zipcode" , length = 100)
    private String zipcode;

    @Builder
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
