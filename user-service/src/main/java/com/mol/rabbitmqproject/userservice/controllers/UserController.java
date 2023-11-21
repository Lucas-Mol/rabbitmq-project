package com.mol.rabbitmqproject.userservice.controllers;

import com.mol.rabbitmqproject.userservice.model.dtos.SignUpInputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public ResponseEntity signUp(@RequestBody SignUpInputDTO inputDTO) {
        System.out.println("Supposedly registering a new user");

        return ResponseEntity.ok().build();
    }
}
