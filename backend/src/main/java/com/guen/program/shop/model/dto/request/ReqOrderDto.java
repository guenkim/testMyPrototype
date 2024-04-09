package com.guen.program.shop.model.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.util.List;


@Schema(description = "주문 정보 DTO")
@Getter
public class ReqOrderDto {

    @Schema(description = "회원 아이디")
    @Min(value = 1, message = "crewId는 0보다 커야 합니다.")
    private Long crewId;

    @Schema(description = "주문 정보 목록")
    private List<com.guen.program.shop.model.dto.request.ReqOrderDto.OrderInfo> orders;

    @JsonCreator
    public void ReqOrderDto(
                                @JsonProperty(value="crewId") final Long crewId,
                                @JsonProperty(value="orders") final List<com.guen.program.shop.model.dto.request.ReqOrderDto.OrderInfo> orders
                            )
    {
        this.crewId = crewId;
        this.orders = orders;
    }


    @Schema(description = "주문 정보")
    @Getter
    public static class OrderInfo{
        @Schema(description = "아이템 아이디")
        @NotNull
        private Integer itemId;

        @Schema(description = "주문 수량")
        @NotNull
        private Integer count;

        @JsonCreator
        public void OrderInfo(
                            @JsonProperty(value = "itemID") final Integer itemId,
                            @JsonProperty(value = "count") final Integer count
                          )
        {
            this.itemId = itemId;
            this.count = count;
        }
    }
}
