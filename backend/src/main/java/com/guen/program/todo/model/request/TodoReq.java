package com.guen.program.todo.model.request;


import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.enumclass.Complete;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "todo 정보")
@Getter
public class TodoReq {

    @Schema(description = "todo 제목", nullable = false, example = "제목" ,defaultValue = "제목")
    @NotBlank
    private String subject;

    @Schema(description = "todo 내용", nullable = true, example = "내용",defaultValue = "내용")
    private String body;

    @Schema(description = "todo 완료 여부", nullable = false, example = "FALSE",defaultValue = "FALSE" )
    private Complete completed = Complete.FALSE;

    @Builder
    public TodoReq(String subject, String body, Complete completed) {
        this.subject = subject;
        this.body = body;
        this.completed = completed;
    }

    public Todo toEntity(){
        return Todo.builder()
                .subject(subject)
                .body(body)
                .completed(completed)
                .build();
    }

}
