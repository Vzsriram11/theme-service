package com.sriram.themevest.dto.error;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private List<String> errors;

}
