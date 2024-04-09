package com.guen.program.shop.api;

import com.guen.error.ErrorResponse;
import com.guen.jwt.auth.UserAuthorize;
import com.guen.program.shop.model.dto.request.ReqCrewDto;
import com.guen.program.shop.model.dto.response.CrewDto;
import com.guen.program.shop.service.CrewService;
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

@Tag(name="회원 api")
@Slf4j
@RestController
@RequestMapping("/api")
@UserAuthorize
@RequiredArgsConstructor
@Validated
public class CrewController {
    private final CrewService crewService;

    @Operation(summary = "회원 목록 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 목록 조회 성공",  content = @Content(schema = @Schema(implementation = CrewDto.class)))
    })
    @GetMapping("/crews")
    public ResponseEntity crews(){
        List<CrewDto> crews = crewService.findCrews();
        return ResponseEntity.ok().body(crews);
    }

    @PostMapping("/crew")
    @Operation(summary = "회원 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원 생성 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity register(@RequestBody @Valid final ReqCrewDto reqCrewDto){
        crewService.saveCrew(reqCrewDto);
        return ResponseEntity.noContent().build();
    }
}
