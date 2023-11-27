package com.mol.rabbitmqproject.userservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public record LogInInputDTO(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        String userIP

) { }
