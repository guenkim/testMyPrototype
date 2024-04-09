package com.guen.program.shop.api;


import com.guen.error.ErrorResponse;
import com.guen.jwt.auth.UserAuthorize;
import com.guen.program.shop.model.dto.request.ReqItemDto;
import com.guen.program.shop.model.dto.request.ReqUpdateItemDto;
import com.guen.program.shop.model.dto.response.ItemDto;
import com.guen.program.shop.repository.item.ItemRepo;
import com.guen.program.shop.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

@Tag(name = "물품 api")
@Slf4j
@RestController
@RequestMapping("/api")
@UserAuthorize
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemRepo itemRepo;

    private final ItemService itemService;

    @Operation(summary = "물품 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "물품 생성 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/item")
    public ResponseEntity create(@RequestBody @Valid final ReqItemDto reqStockDto) {
        itemService.saveItem(reqStockDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "아이템 목록 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아이템 목록 조회 성공", content = @Content(schema = @Schema(implementation = ItemDto.class)))
    })
    @GetMapping("/items")
    public ResponseEntity items() {

        List<ItemDto> itemDtos = itemService.findAll();
        return ResponseEntity.ok().body(itemDtos);
    }


    @Operation(summary = "아이템 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아이템 정보 조회 성공", content = @Content(schema = @Schema(implementation = ItemDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/items/{itemId}")
    public ResponseEntity item(
            @Parameter(description = "아이템 아이디", required = true, in = ParameterIn.PATH)
            @PathVariable final Long itemId) {
        ItemDto itemDto = itemService.findById(itemId);
        return ResponseEntity.ok().body(itemDto);
    }

    @Operation(summary = "물품 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "물품 수정 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/items/{itemId}")
    public ResponseEntity editItem
            (
                    @Parameter(description = "아이템 아이디", required = true, in = ParameterIn.PATH)
                    @PathVariable final Long itemId,
                    @RequestBody @Valid final ReqUpdateItemDto reqStockDto
            ) {
            itemService.updateItem(itemId,reqStockDto);
            return ResponseEntity.noContent().build();
    }

    @Operation(summary = "아이템 삭제") // @Operation : API 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "아이템 삭제 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity deleteItem(
            @Parameter(description = "아이템 아이디",required = true, in = ParameterIn.PATH)
            @PathVariable(value = "itemId", required = true) final Long itemId
    ){
        itemService.deleteById(itemId);
        return ResponseEntity.noContent().build();
    }


}
