package com.guen.program.shop.model.dto.request;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.guen.program.shop.model.entity.Address;
import com.guen.program.shop.model.entity.Crew;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(description = "회원 정보")
@Getter
public class ReqCrewDto {

    @Schema(description = "회원이름" , nullable = false)
    @NotBlank
    private String name;

    @Schema(description = "도시" , nullable = false)
    @NotBlank
    private String city;

    @Schema(description = "거리명" , nullable = false)
    @NotBlank
    private String street;

    @Schema(description = "zipcode" , nullable = false)
    @NotBlank
    private String zipcode;

    @JsonCreator
    public ReqCrewDto(@JsonProperty("name") String name,@JsonProperty("city") String city, @JsonProperty("street") String street,@JsonProperty("zipcode") String zipcode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public Crew toEntity(){
        return Crew.builder()
                .address(Address.builder().city(this.city).street(this.street).zipcode(this.zipcode).build())
                .name(this.name)
                .build();
    }
}
