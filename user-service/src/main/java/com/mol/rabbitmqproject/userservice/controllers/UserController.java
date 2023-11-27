package com.mol.rabbitmqproject.userservice.controllers;

import com.mol.rabbitmqproject.userservice.model.dto.*;
import com.mol.rabbitmqproject.userservice.service.UserService;
import jakarta.validation.Valid;
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
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody @Valid SignUpInputDTO inputDTO) {
        var response = userService.signUp(inputDTO);

        return response ? ResponseEntity.ok("User supposedly signed up") : ResponseEntity.badRequest().build();
    }

    @PostMapping("/log-in")
    public ResponseEntity logIn(@RequestBody @Valid LogInInputDTO inputDTO) {
        return userService.logIn(inputDTO);
    }


}
