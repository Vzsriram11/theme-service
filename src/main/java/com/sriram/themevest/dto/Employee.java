package com.sriram.themevest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
    @NotBlank
    private String name;

    @Min(value=18, message = "Age must be at least 18")
    @Max(value=58, message  ="Age can be at most 58")
    private Integer age;

    @Email
    private String email;
}
