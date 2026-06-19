package com.sriram.themevest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateThemeRequest {

    @NotBlank
    private String name;

    private String description;
    @NotBlank
    private String riskLevel;
}