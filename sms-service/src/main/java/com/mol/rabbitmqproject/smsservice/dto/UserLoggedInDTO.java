package com.mol.rabbitmqproject.smsservice.dto;

public record UserLoggedInDTO(
        String username,
        String phoneNumber,
        String token
) { }
