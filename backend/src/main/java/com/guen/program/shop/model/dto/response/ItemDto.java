package com.guen.program.shop.model.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.guen.common.annotation.EnumNotNull;
import com.guen.program.shop.model.dto.request.ReqItemDto;
import com.guen.program.shop.model.enumclass.BatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "아이템 정보")
@Getter
public class ItemDto {

    @Schema(description = "상품 아이디", example = "아이디")
    private Long id;

    @Schema(description = "상품명", example = "상품명")
    private String name;

    @Schema(description = "가격", example = "가격")
    private int price;


    @Schema(description = "재고", example = "재고")
    private int stockQuanitty;

    @Schema(description = "상품구분, STOCK : COIN", example = "STOCK")
    private BatType batType;

    @Schema(description = "틱커 , STOCK일 경우 입력", example = "BIDU")
    private String ticker;

    @Schema(description = "코인회사, COIN일 경우 입력", example = "BITCOIN")
    private String company;

    @Schema(description = "상품 카테고리 아이디 목록")
    private List<ItemCategory> categories = new ArrayList<>();

    @Builder
    public ItemDto(Long id,String name, int price, int stockQuanitty, BatType batType, String ticker, String company, List<ItemCategory> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuanitty = stockQuanitty;
        this.batType = batType;
        this.ticker = ticker;
        this.company = company;
        this.categories = categories;
    }

    @Getter
    @Schema(description = "상품 카테고리 정보")
    public static class ItemCategory{
        @Schema(description = "상품 카테고리 아이디", nullable = false, example = "상품 카테고리 아이디")
        private long id;

        @Schema(description = "상품 카테고리 명", nullable = false, example = "상품 카테고리 명")
        private String name;

        @Builder
        public ItemCategory(long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

}
