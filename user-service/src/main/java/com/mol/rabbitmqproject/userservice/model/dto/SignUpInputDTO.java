package com.mol.rabbitmqproject.userservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public record SignUpInputDTO(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        String email
) { }
