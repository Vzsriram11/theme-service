package com.sriram.themevest.dto;

import jakarta.validation.constraints.NotBlank;



public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password

) {
}
