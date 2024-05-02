package ars.otus.sonet.dialog.advice;

import ars.otus.sonet.dialog.model.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static ars.otus.sonet.dialog.model.enums.ErrorCodes.DEFAULT_ERROR_CODE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity commonException(Exception ex) {
        log.error("Ошибка приложения времени выполнения. {}",
                ex.getMessage());
        return ResponseEntity.internalServerError().body(
                ErrorResponse.builder()
                        .code(DEFAULT_ERROR_CODE.getInternalErrorCode())
                        .message(ex.getMessage())
                        .build());
    }
}
