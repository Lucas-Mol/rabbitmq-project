package com.mol.rabbitmqproject.smsservice.dto;

public record UserLoggedInDTO(
        String username,
        String smsPhoneNumber,
        String token
) { }
