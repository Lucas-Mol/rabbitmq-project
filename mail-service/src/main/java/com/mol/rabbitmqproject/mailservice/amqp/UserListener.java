package com.mol.rabbitmqproject.mailservice.amqp;

import com.mol.rabbitmqproject.mailservice.model.dto.UserRegisteredDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserListener {

    @RabbitListener(queues = "mail.users.registered")
    public void receiveUserRegisteredMail(UserRegisteredDTO userRegistered){
        System.out.println("Supposedly sending email to " + userRegistered.email());
    }
}
