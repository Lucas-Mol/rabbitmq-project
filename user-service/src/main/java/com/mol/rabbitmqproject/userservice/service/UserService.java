package com.mol.rabbitmqproject.userservice.service;

import com.mol.rabbitmqproject.userservice.exceptions.Not2FAUserException;
import com.mol.rabbitmqproject.userservice.model.dto.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*
        A registered users' list instead of persist it on a DB (It's a RabbitMQ lab application, don't wanna make it harder)
     */
    private List<SignUpInputDTO> users = new ArrayList<>();

    private Map<String, Set<String>> userIPs = new HashMap<>();

    public boolean signUp(SignUpInputDTO inputDTO) {
        try {
            users.add(inputDTO);


            System.out.println("Supposedly registering a new user: " + inputDTO.username() +
                    " email: " + inputDTO.email() + get2FAText(inputDTO));

            var messagingOutput = new SignUpRabbitMQOutputDTO(inputDTO.username(),
                    inputDTO.email(),
                    getUserScore(inputDTO));

            rabbitTemplate.convertAndSend("user_registered_event_exchange", "", messagingOutput);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public ResponseEntity logIn(LogInInputDTO inputDTO) {
        var userFoundOptional = findUserOptional(inputDTO);

        if(userNotFound(userFoundOptional))
            return ResponseEntity.ok("User not found");

        var userFound = userFoundOptional.get();

        try {
            manage2FA(userFound);
            return ResponseEntity.ok("Using SMS 2FA");
        } catch (Not2FAUserException ex) {
            //keep going...
        } catch (Exception ex) {
            throw ex;
        }

        manageNewLocation(userFound, inputDTO.userIP());

        System.out.println("User supposedly logged in");
        return ResponseEntity.ok("User supposedly logged in");
    }

    private void manageNewLocation(SignUpInputDTO user, String IP) {
        if(!userIPs.containsKey(user.username())) {
            var ipTempSet = new HashSet<String>();
            ipTempSet.add(IP);

            userIPs.put(user.username(), ipTempSet);
        } else {
            var userIpsSet = userIPs.get(user.username());

            if(!userIpsSet.contains(IP)) {
                userIpsSet.add(IP);
                userIPs.put(user.username(), userIpsSet);

                //Supossedly checking IP location on some third party API

                var messagingOutput = new LogInLocationEmailRabbitMQOutputDTO(  user.username(),
                        user.email(),
                        IP);

                rabbitTemplate.convertAndSend("user.new.location.mail.queue", messagingOutput);
                System.out.println("Sending new location email");
            }
        }
    }

    private void manage2FA(SignUpInputDTO user) throws Not2FAUserException{
        if(user.smsPhoneNumber() != null) {
            var random = new Random();
            var token2FA = random.nextInt(900000) + 100000;

            var messagingOutput = new LogIn2FASMSRabbitMQOutputDTO(
                    user.username(),
                    user.smsPhoneNumber(),
                    String.valueOf(token2FA)
            );

            rabbitTemplate.convertAndSend("user.login.2fa.SMS.queue", messagingOutput);

            System.out.println("Using SMS 2FA: " + user.smsPhoneNumber());
        } else {
            throw new Not2FAUserException();
        }
    }

    private boolean userNotFound(Optional<SignUpInputDTO> userFoundOptional) {
        if(!userFoundOptional.isPresent()) {
            System.out.println("User not found");
            return true;
        }
        return false;
    }

    private Optional<SignUpInputDTO> findUserOptional(LogInInputDTO inputDTO) {
        return users.stream()
                .filter(user -> user.username().equals(inputDTO.username())
                        && user.password().equals(inputDTO.password()))
                .findFirst();
    }

    private String get2FAText(SignUpInputDTO inputDTO) {
        return isUserUsing2FA(inputDTO) ? " and the 2FA is ON" : "";
    }

    private String getUserScore(SignUpInputDTO inputDTO) {
        return isUserUsing2FA(inputDTO) ? "20" : "0";
    }

    private boolean isUserUsing2FA(SignUpInputDTO inputDTO) {
        return inputDTO.smsPhoneNumber() != null;
    }
}
