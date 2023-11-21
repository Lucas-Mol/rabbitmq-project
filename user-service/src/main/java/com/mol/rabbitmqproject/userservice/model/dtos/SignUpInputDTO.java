package com.mol.rabbitmqproject.userservice.model.dtos;

public record SignUpInputDTO(
        String username,
        String password,
        String email
) { }
