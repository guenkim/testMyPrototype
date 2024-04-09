package com.guen.common.file.api;

import com.guen.common.file.exception.FileNotFoundException;
import com.guen.common.file.service.FileStorageService;
import com.guen.jwt.auth.UserAuthorize;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.io.Resource;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.guen.error.ErrorResponse;
import java.io.IOException;


@Hidden
@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "File API")
@UserAuthorize
@RequiredArgsConstructor
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;


    @Operation(summary = "파일 다운로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "다운로드 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재 하지 않는 파일 입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/file/{fileId}")
    public ResponseEntity<Resource> downloadFile(
            @Parameter(description = "파일 아이디", required = true, in = ParameterIn.PATH)
            @PathVariable final String fileId,
            HttpServletRequest request) throws IOException, FileNotFoundException {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(Long.parseLong(fileId));

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Operation(summary = "todo 삭제") // @Operation : API 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "다운로드 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/file/{fileId}")
    public ResponseEntity delete(
            @Parameter(description = "파일 아이디",required = true, in = ParameterIn.PATH)
            @PathVariable(value = "fileId", required = true) final String fileId
    ) {
        log.info("FileController > delete");
        fileStorageService.deleteFileById(Long.parseLong(fileId));
        return ResponseEntity.noContent().build();
    }

}
