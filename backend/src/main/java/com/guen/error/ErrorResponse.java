package com.guen.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "에러 전문")
@Getter
public class ErrorResponse {

    @Schema(description = "에러 메시지")
    private String message;
    @Schema(description = "에러 코드")
    private String code;
    @Schema(description = "http 상태 코드")
    private int status;
    @Schema(description = "필드 에러 리스트")
    private List<FieldError> errors = new ArrayList<>();

    @Builder
    public ErrorResponse(String message, String code, int status, List<FieldError> errors) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.errors = initErrors(errors);
    }

    private List<FieldError> initErrors(List<FieldError> errors) {
        return (errors == null) ? new ArrayList<>() : errors;
    }

    @Schema(description = "필드 에러 정보")
    @Getter
    public static class FieldError {
        @Schema(description = "필드명")
        private String field;
        @Schema(description = "필드 값")
        private String value;
        @Schema(description = "에러 원인")
        private String reason;

        @Builder
        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }

}
