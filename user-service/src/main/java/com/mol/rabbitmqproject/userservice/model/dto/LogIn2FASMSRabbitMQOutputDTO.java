package com.mol.rabbitmqproject.userservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public record LogIn2FASMSRabbitMQOutputDTO(
        @NotBlank
        String username,
        @NotBlank
        String smsPhoneNumber,
        @NotBlank
        String token

) { }
