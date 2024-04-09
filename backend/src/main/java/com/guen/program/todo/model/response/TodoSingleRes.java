package com.guen.program.todo.model.response;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.enumclass.Complete;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "todo 정보")
@Getter
public class TodoSingleRes {

    @Schema(description = "todo 아이디", example = "todo 아이디")
    private Long id;

    @Schema(description = "todo 제목", example = "제목")
    private String subject;

    @Schema(description = "todo 내용",  example = "내용")
    private String body;

    @Schema(description = "todo 완료 여부", example = "FALSE")
    private Boolean completed;

    @Schema(description = "todo 첨부 파일")
    private List<FileInfo> files = new ArrayList<>();

    @Schema(description = "todo 등록일", example = "2021-01-01 00:00:00", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regdt;

    @Schema(description = "todo 수정일", example = "2021-01-01 00:00:00", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime moddt;

    @Builder
    public TodoSingleRes(Long id, String subject, String body, Boolean completed, List<FileInfo> files, LocalDateTime regdt, LocalDateTime moddt) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.completed = completed;
        this.files = files;
        this.regdt = regdt;
        this.moddt = moddt;
    }

    @Getter
    @Schema(description = "todo 첨부 파일 정보", nullable = true)
    public static class FileInfo{

        @Schema(description = "todo 파일 아이디", nullable = true)
        private Long id;

        @Schema(description = "todo 파일 아이디명", nullable = true)
        private String name;

        @Builder
        public FileInfo(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}


