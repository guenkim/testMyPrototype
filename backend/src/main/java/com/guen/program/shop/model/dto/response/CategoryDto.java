package com.guen.program.shop.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "카테고리 정보")
@Getter
public class CategoryDto {

    @Schema(description = "카테고리 아이디")
    private long id;

    @Schema(description = "카테고리명")
    private String name;

    @Builder
    public CategoryDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
