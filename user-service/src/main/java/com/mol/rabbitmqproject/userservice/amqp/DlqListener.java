package com.mol.rabbitmqproject.userservice.amqp;

import com.mol.rabbitmqproject.userservice.model.dto.SignUpRabbitMQOutputDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DlqListener {

    @RabbitListener(queues = "mail.users.registered.dlq")
    public void receiveUserRegisteredMail(SignUpRabbitMQOutputDTO registeredUser){
        System.out.println("Supposedly persisting user " + registeredUser.username() + " as blocked because it can't receive emails");
    }

}
