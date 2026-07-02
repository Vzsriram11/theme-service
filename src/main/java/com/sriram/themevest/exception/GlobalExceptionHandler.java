package com.sriram.themevest.exception;

import com.sriram.themevest.dto.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(ThemeNotFoundException.class)
public ResponseEntity<ErrorResponse> handleThemeNotFoundException(ThemeNotFoundException themeEx)
{
    ErrorResponse response = ErrorResponse.builder().timestamp(LocalDateTime.now()).
            status(HttpStatus.NOT_FOUND.value()).message(themeEx.getMessage()).errors(List.of()).build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

}



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {
//List<String> errors2 = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + ": " + error.getDefaultMessage())
                .toList();
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed")
                .errors(errors)
                .build();

        //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
         return ResponseEntity .badRequest().body(response);
    }



}