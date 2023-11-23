package com.mol.rabbitmqproject.scoreservice.dto;

public record UserRegisteredDTO(
        String username,
        String score
) { }
