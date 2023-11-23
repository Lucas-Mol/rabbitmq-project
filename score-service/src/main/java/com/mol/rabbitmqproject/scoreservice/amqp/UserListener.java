package com.mol.rabbitmqproject.scoreservice.amqp;

import com.mol.rabbitmqproject.scoreservice.dto.UserRegisteredDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserListener {

    @RabbitListener(queues = "score.users.registered")
    public void receiveUserRegisteredMail(UserRegisteredDTO userRegistered){
        System.out.println("Supposedly registering a new user " + userRegistered.username() +
                " on scoring service with " + userRegistered.score() + " points");
    }
}
