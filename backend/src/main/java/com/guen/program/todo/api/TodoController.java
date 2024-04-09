package com.guen.program.todo.api;

import com.guen.common.model.dto.PageRequest;
import com.guen.common.model.dto.PageResponse;
import com.guen.error.ErrorResponse;
import com.guen.jwt.auth.UserAuthorize;
import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.enumclass.Complete;
import com.guen.program.todo.model.request.TodoReq;
import com.guen.program.todo.model.response.TodoSingleRes;
import com.guen.program.todo.service.TodoService;

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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Todo API")
@Slf4j
@RestController
@RequestMapping("/api/todos")
@UserAuthorize
@RequiredArgsConstructor
@Validated
//@Hidden
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "todo 목록 반환")
    //@Hidden
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "todo 목록 조회 성공",  content = @Content(schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "미인증", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity getTodos(
            @ParameterObject @Valid final PageRequest pageRequest
    ){
        log.info("TodoController > getTodos");
        PageResponse response = todoService.search(pageRequest.getSubject(), pageRequest.of());

        return ResponseEntity.ok().body(response);

    }


    @Operation(summary = "todo 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "todo 정보 조회 성공",  content = @Content(schema = @Schema(implementation = TodoSingleRes.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "미인증", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/{todoId}")
    public ResponseEntity getTodo(
            @Parameter(description = "todo 아이디", required = true, in = ParameterIn.PATH)
            @PathVariable(value = "todoId", required = true) final String todoId
    ) {
        log.info("TodoController > getTodo");
        return ResponseEntity.ok().body(todoService.findById(todoId));
    }

    @Operation(summary = "todo 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "todo 생성 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "미인증", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity create(
            @Parameter(description = "The todoReq part of the request",required = true)
            @RequestPart(value="todoReq", required = true) @Valid final TodoReq todoReq,
            @Parameter(description = "The files part of the request",required = false)
            @RequestPart(value = "files", required = false) final  List<MultipartFile> files
    ){
        log.info("TodoController > create");
        Todo todo = todoService.save(todoReq,files);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "todo 수정") // @Operation : API 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "todo 수정 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "미인증", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping(value="/{todoId}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity update(
            @Parameter(description = "todo 아이디", required = true, in = ParameterIn.PATH)
            @PathVariable(value = "todoId", required = true) final String todoId,
            @Parameter(description = "The todoReq part of the request",required = true)
            @RequestPart(value="todoReq", required = true) @Valid final TodoReq todoReq,
            @Parameter(description = "The files part of the request",required = false)
            @RequestPart(value = "files", required = false) final  List<MultipartFile> files
    ) {
        log.info("TodoController > update");
        todoService.updateById(todoId,todoReq,files);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "todo 삭제") // @Operation : API 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "todo 삭제 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "미인증", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{todoId}")
    public ResponseEntity delete(
            @Parameter(description = "todo 아이디",required = true, in = ParameterIn.PATH)
            @PathVariable(value = "todoId", required = true) final String todoId
    ) {
        log.info("TodoController > delete");
        todoService.remove(todoId);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "todo 완료 여부 설정") // @Operation : API 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "todo 완료 여부 설정 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "미인증", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{todoId}/{completed}")
    public ResponseEntity updateComplete(
            @Parameter(description = "todo 아이디", required = true, in = ParameterIn.PATH)
            @PathVariable(value = "todoId", required = true) final String todoId,
            @Parameter(description = "todo 완료 여부", required = true, in = ParameterIn.PATH)
            @PathVariable(value = "completed", required = true)  final Complete completed
    ) {
        log.info("TodoController > updateComplete");
        todoService.updateCompleteById(todoId,completed);
        return ResponseEntity.noContent().build();
    }
}
