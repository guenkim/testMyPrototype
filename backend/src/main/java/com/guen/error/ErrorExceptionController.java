package com.guen.error;


import com.guen.jwt.exception.ExpiredRefreshJwtException;
import com.guen.program.shop.exception.ItemNotFindException;
import com.guen.program.shop.exception.NotEnoughStockException;
import com.guen.program.shop.exception.NotMemberException;
import com.guen.program.shop.exception.OrderNotFindException;
import com.guen.program.todo.exception.PathVariableException;
import com.guen.program.todo.exception.TodoNotFindException;
import com.guen.sign.Exception.NotValidIdOrPasswordException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ErrorExceptionController {

    /*******************************************************************************
     * SpringSecurity - GLOBAL 에러 핸들링
     *******************************************************************************/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
        return buildFieldErrors(ErrorCode.INPUT_VALUE_INVALID, fieldErrors);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.INPUT_VALUE_INVALID);
    }


    @ExceptionHandler(NotEnoughStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotEnoughStockException(NotEnoughStockException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.NotEnoughStockException);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleBindException(BindException e) {
        final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
        return buildFieldErrors(ErrorCode.INPUT_VALUE_INVALID, fieldErrors);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.INPUT_VALUE_INVALID);
    }

    @ExceptionHandler(NotValidIdOrPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleNotValidIdOrPasswordException(NotValidIdOrPasswordException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.NotValidIdOrPasswordException);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTodoNotFindException(ConstraintViolationException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.ConstraintViolationException);
    }

    @ExceptionHandler(NotMemberException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotMemberException(NotMemberException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.NotMemberException);
    }

    @ExceptionHandler(OrderNotFindException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotMemberException(OrderNotFindException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.OrderNotFindException);
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleInternalServerException(Exception e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(NotEnoughStockException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected ErrorResponse handleNotEnoughStockException(NotEnoughStockException e) {
//        log.error(e.getMessage());
//        return buildError(ErrorCode.INTERNAL_SERVER_ERROR);
//    }

    /*******************************************************************************
     * SpringSecurity - JWT 토큰 관련 에러 핸들링
     *******************************************************************************/
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.AccessDenied);
    }


    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleSignatureException(SignatureException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.Signature);
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleMalformedJwtException(MalformedJwtException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.MalformedJwt);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleExpiredJwtException(ExpiredJwtException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.ExpiredJwtException);
    }

    @ExceptionHandler(ExpiredRefreshJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleExpiredRefreshJwtException(ExpiredRefreshJwtException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.ExpiredRefreshJwtException);
    }


    /*******************************************************************************
     * 사용자 에러 핸들링
     *******************************************************************************/

    @ExceptionHandler(TodoNotFindException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTodoNotFindException(TodoNotFindException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.TodoNotFindException);
    }

    @ExceptionHandler(ItemNotFindException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleItemNotFindException(ItemNotFindException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.ItemNotFindException);
    }

    @ExceptionHandler(PathVariableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTodoNotFindException(PathVariableException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.PathVariableException);
    }



    // TODO: 2018. 5. 12. 비밀번호 변경 컨트롤러 생성시 주석 해제할것 -yun
//    @ExceptionHandler(PasswordFailedExceededException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    protected ErrorResponse handlePasswordFailedExceededException(PasswordFailedExceededException e) {
//        log.error(e.getMessage());
//        return buildError(e.getErrorCode());
//    }

    private List<ErrorResponse.FieldError> getFieldErrors(BindingResult bindingResult) {
        final List<FieldError> errors = bindingResult.getFieldErrors();
        return errors.parallelStream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .reason(error.getDefaultMessage())
                        .field(error.getField())
                        .value(Objects.requireNonNull(error.getRejectedValue().toString(),""))
                        .build())
                .collect(Collectors.toList());
    }


    private ErrorResponse buildError(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
    }

    private ErrorResponse buildFieldErrors(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .errors(errors)
                .build();
    }
}
