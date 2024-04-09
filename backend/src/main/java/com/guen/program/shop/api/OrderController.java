package com.guen.program.shop.api;

import com.guen.error.ErrorResponse;
import com.guen.jwt.auth.UserAuthorize;
import com.guen.program.shop.model.dto.request.ReqOrderDto;
import com.guen.program.shop.model.dto.request.ReqOrderSearchDto;
import com.guen.program.shop.model.dto.response.OrderDto;
import com.guen.program.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "주문 api")
@Slf4j
@RestController
@RequestMapping("/api")
@UserAuthorize
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "주문 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "주문 생성 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/order")
    public ResponseEntity create(
            @Parameter(description = "crew 아이디", required = true, in = ParameterIn.QUERY)
            @RequestParam(name="crewId" , required = true) final Long crewId,
            @Parameter(description = "아이템 아이디", required = true, in = ParameterIn.QUERY)
            @RequestParam(name="itemId" , required = true) final Long itemId,
            @Parameter(description = "주문 수량", required = true, in = ParameterIn.QUERY)
            @RequestParam(name="count" , required = true) @Valid @Positive @Digits(integer = 5, fraction = 0) final int count
    ) throws Exception {

        orderService.createOrder(crewId,itemId,count);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "복수 아이템 주문 생성")
    @ApiResponses(value={
            @ApiResponse(responseCode = "204" , description = "복수건 주문 생성 성공" , content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/multiOrder")
    public ResponseEntity multiCreate(@RequestBody @Valid final ReqOrderDto reqOrderDto)throws Exception{
        orderService.createMultipleOrder(reqOrderDto);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "주문 목록 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 목록 조회 성공",  content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "404", description = "회원을 못찾아서 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "주문을 못찾아서 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/orders")
    public ResponseEntity orderList(@ParameterObject @Valid final ReqOrderSearchDto orderSearch) {
        OrderDto orders = orderService.findOrders(orderSearch);
        return ResponseEntity.ok().body(orders);
    }


    @Operation(summary = "주문 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 취소 성공",  content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "주문을 못찾아서 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/orders/{orderId}")
    public ResponseEntity cancel(
            @Parameter(description = "주문 아이디" ,required = true , in = ParameterIn.PATH)
            @PathVariable(name = "orderId" , required = true) final Long orderId
    ){
        orderService.cancel(orderId);
        return ResponseEntity.noContent().build();
    }



}
