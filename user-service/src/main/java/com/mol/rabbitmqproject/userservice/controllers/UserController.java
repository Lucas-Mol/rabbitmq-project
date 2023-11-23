package com.mol.rabbitmqproject.userservice.controllers;

import com.mol.rabbitmqproject.userservice.model.dto.SignUpInputDTO;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseEntity signUp(@RequestBody @Valid SignUpInputDTO inputDTO) {
        System.out.println("Supposedly registering a new user: " + inputDTO.username() + " email: " + inputDTO.email());

        rabbitTemplate.convertAndSend("users.registered", inputDTO);
        return ResponseEntity.ok().build();
    }
}
