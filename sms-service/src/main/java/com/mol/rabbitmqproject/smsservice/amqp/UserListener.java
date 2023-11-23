package com.mol.rabbitmqproject.smsservice.amqp;

import com.mol.rabbitmqproject.smsservice.dto.UserLoggedInDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserListener {

    @RabbitListener(queues = "users.logged-in")
    public void receiveUserRegisteredMail(UserLoggedInDTO userLoggedIn){
        System.out.println("""
                Supposedly sending SMS to %s
                --------------------
                Phone Number: %s 
                Token sent: %s
                """.formatted(
                        userLoggedIn.username(),
                        userLoggedIn.phoneNumber(),
                        userLoggedIn.token()
                ));
    }
}
