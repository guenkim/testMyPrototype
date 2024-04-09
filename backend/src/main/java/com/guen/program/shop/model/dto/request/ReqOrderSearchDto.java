package com.guen.program.shop.model.dto.request;

import com.guen.common.annotation.EnumNotNull;
import com.guen.program.shop.model.enumclass.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Schema(description = "주문 조회 정보")
@Getter
public class ReqOrderSearchDto {

    @Schema(description = "회원아이디", nullable = false, example = "1" ,defaultValue = "1")
    @NotNull
    @Positive
    private Long crewId;

    @Schema(description = "주문상태 [ORDER | CANCEL]", nullable = false, example = "ORDER" ,defaultValue = "ORDER")
    @EnumNotNull
    @NotNull
    private OrderStatus orderStatus;

    public ReqOrderSearchDto(Long crewId, OrderStatus orderStatus) {
        this.crewId = crewId;
        this.orderStatus = orderStatus;
    }
}
