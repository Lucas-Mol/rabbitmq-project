package com.mol.rabbitmqproject.userservice.controllers;

import com.mol.rabbitmqproject.userservice.model.dto.SignUpInputDTO;
import com.mol.rabbitmqproject.userservice.model.dto.SignUpRabbitMQOutputDTO;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private List<SignUpInputDTO> users = new ArrayList<>();

    @PostMapping
    public ResponseEntity signUp(@RequestBody @Valid SignUpInputDTO inputDTO) {
        users.add(inputDTO);

        System.out.println("Supposedly registering a new user: " + inputDTO.username() + " email: " + inputDTO.email() +
                (isUserUsing2FA(inputDTO) ? " and the 2FA is ON" : ""));

        var messagingOutput = new SignUpRabbitMQOutputDTO(  inputDTO.username(),
                                                            inputDTO.email(),
                                                            getUserScore(inputDTO));

        rabbitTemplate.convertAndSend("user_registered_event_exchange", "", messagingOutput);
        return ResponseEntity.ok().build();
    }

    private String getUserScore(SignUpInputDTO inputDTO) {
        return isUserUsing2FA(inputDTO) ? "20" : "0";
    }

    private boolean isUserUsing2FA(SignUpInputDTO inputDTO) {
        return inputDTO.smsPhoneNumber() != null || inputDTO.authenticatorProvider() != null;
    }
}
