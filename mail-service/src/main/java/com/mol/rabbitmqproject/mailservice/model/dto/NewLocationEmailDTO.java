package com.mol.rabbitmqproject.mailservice.model.dto;

public record NewLocationEmailDTO(
        String username,
        String email,
        String userIp

) { }
