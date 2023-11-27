package com.mol.rabbitmqproject.userservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public record LogInLocationEmailRabbitMQOutputDTO(
        @NotBlank
        String username,
        @NotBlank
        String email,
        @NotBlank
        String userIp

) { }
