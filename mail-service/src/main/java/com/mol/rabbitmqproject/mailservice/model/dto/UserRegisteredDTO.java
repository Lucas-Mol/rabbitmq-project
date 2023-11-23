package com.mol.rabbitmqproject.mailservice.model.dto;

public record UserRegisteredDTO(
        String username,
        String password,
        String email
) { }
