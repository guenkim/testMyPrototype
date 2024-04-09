package com.guen.program.shop.model.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.guen.common.annotation.EnumNotNull;
import com.guen.program.shop.model.enumclass.BatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "아이템 정보")
@Getter
public class ReqUpdateItemDto {

    @Schema(description = "상품명", nullable = false, example = "상품명" ,defaultValue = "바이두")
    @NotBlank
    private String name;

    @Schema(description = "가격", nullable = false, example = "가격" ,defaultValue = "99999999")
    @Positive
    @NotNull
    private int price;


    @Schema(description = "재고", nullable = false, example = "재고",defaultValue = "99999999")
    @Positive
    @NotNull
    private int stockQuanitty;

    @Schema(description = "상품구분, STOCK : COIN", nullable = false, example = "STOCK" ,defaultValue = "STOCK")
    @EnumNotNull
    @NotNull
    private BatType batType;

    @Schema(description = "틱커 , STOCK일 경우 입력", nullable = true, example = "BIDU" ,defaultValue = "BIDU")
    private String ticker;

    @Schema(description = "코인회사, COIN일 경우 입력", nullable = true, example = "BITCOIN" ,defaultValue = "BITCOIN")
    private String company;

    @Schema(description = "상품카테고리 아이디 목록")
    private List<Category> categories = new ArrayList<>();

    @JsonCreator
    public ReqUpdateItemDto(@JsonProperty("name") String name, @JsonProperty("price") int price, @JsonProperty("stockQuanitty") int stockQuanitty, @JsonProperty("batType") BatType batType, @JsonProperty("ticker") String ticker, @JsonProperty("company") String company, @JsonProperty("categories") List<ReqUpdateItemDto.Category> categories) {
        this.name = name;
        this.price = price;
        this.stockQuanitty = stockQuanitty;
        this.batType = batType;
        this.ticker = ticker;
        this.company = company;
        this.categories = categories;
    }



    @Schema(description = "상품 카테고리")
    @Getter
    public static class Category{
        @Schema(description = "상품 카테고리 아이디", nullable = false, example = "아이디" ,defaultValue = "1")
        private Long id;

        @JsonCreator
        public Category(@JsonProperty("id") Long id) {
            this.id = id;
        }
    }
}
