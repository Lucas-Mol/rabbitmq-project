package com.mol.rabbitmqproject.userservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public record SignUpRabbitMQOutputDTO(
        @NotBlank
        String username,
        @NotBlank
        String email,
        @NotBlank
        String score

) { }
