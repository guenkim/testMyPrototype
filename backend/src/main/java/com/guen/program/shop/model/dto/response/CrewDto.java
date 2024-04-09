package com.guen.program.shop.model.dto.response;


import com.guen.program.shop.model.entity.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "회원 정보")
@Getter
public class CrewDto {
    @Schema(description = "회원아이디")
    private Long id;

    @Schema(description = "회원이름")
    private String name;

    @Schema(description = "회원 주소 정보")
    private Address address;

    @Builder
    public CrewDto(Long id ,String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
