package com.guen.common.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;


@Schema(description = "페이지 정보")
@Getter
public final class PageRequest {

    @Schema(description = "검색어", example = "제목", required = false)
    private String subject;

    @Schema(description = "요청페이지", example = "1", required = false)
    @Positive
    private int page = 1;

    @Schema(description = "페이지당 아티클수", example = "10", required = false)
    @Positive
    private int size = 5;


    @JsonIgnore
    private List<Sort.Order> sortOrder = new ArrayList<>();


    @Schema(description = "페이지 정렬 정보", example = "id,asc,subject,desc")
    private String sort;

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setSort(String sort) {
        this.sort = sort;
        if (sort != null && !sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            for (int i = 0; i < sortParams.length; i += 2) {
                String fieldName = sortParams[i];
                Sort.Direction direction = Sort.Direction.fromString(sortParams[i + 1]);
                this.sortOrder.add(Sort.Order.by(fieldName).with(direction));
            }
        }
    }

    public void setPage(String page) {
        try {
            this.page = Integer.parseInt(page) <= 0 ? 1 : Integer.parseInt(page);
        } catch (NumberFormatException e) {
            this.page = 1;
        }
    }
    public void setSize(String size) {
        try {
            this.size = Integer.parseInt(size);
        } catch (NumberFormatException e) {
            int DEFAULT_SIZE = 5;
            int MAX_SIZE = 50;
            this.size = DEFAULT_SIZE;
        }
    }

    public org.springframework.data.domain.PageRequest of() {
        if (sortOrder.size() > 0) {
            return org.springframework.data.domain.PageRequest.of(page - 1, size, Sort.by(sortOrder));
        }else{
            return org.springframework.data.domain.PageRequest.of(page - 1, size);
        }
    }
}
