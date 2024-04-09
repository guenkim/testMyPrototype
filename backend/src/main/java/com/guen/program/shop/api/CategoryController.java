package com.guen.program.shop.api;

import com.guen.error.ErrorResponse;
import com.guen.jwt.auth.UserAuthorize;
import com.guen.program.shop.model.dto.request.ReqCategoryDto;
import com.guen.program.shop.model.dto.response.CategoryDto;
import com.guen.program.shop.model.entity.Category;
import com.guen.program.shop.repository.category.CategoryRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name="카테고리 api")
@Slf4j
@RestController
@RequestMapping("/api")
@UserAuthorize
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryRepo categoryRepo;

    @Operation(summary = "카테고리 목록 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 목록 조회 성공",  content = @Content(schema = @Schema(implementation = CategoryDto.class)))
    })
    @GetMapping("/categories")
    public ResponseEntity categories(){
        List<CategoryDto> categoryDtos = categoryRepo.findAll().stream().map(category ->
            CategoryDto.builder()
                    .name(category.getName())
                    .id(category.getId())
                    .build()
        ).collect(Collectors.toList());
        return ResponseEntity.ok().body(categoryDtos);
    }

    @PostMapping("/category")
    @Operation(summary = "카테고리 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "카테고리 생성 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity create(@RequestBody @Valid final ReqCategoryDto reqCategoryDto){
        categoryRepo.save(Category.builder().name(reqCategoryDto.getName()).build());
        return ResponseEntity.noContent().build();
    }
}
