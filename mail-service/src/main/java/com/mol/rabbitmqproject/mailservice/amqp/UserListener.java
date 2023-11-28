package com.mol.rabbitmqproject.mailservice.amqp;

import com.mol.rabbitmqproject.mailservice.model.dto.NewLocationEmailDTO;
import com.mol.rabbitmqproject.mailservice.model.dto.UserRegisteredDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UserListener {

    @RabbitListener(queues = "mail.users.registered")
    public void receiveUserRegisteredMail(UserRegisteredDTO registeredUser){
        simulateError();

        System.out.println("Supposedly sending a welcome mail to " + registeredUser.email());
    }

    @RabbitListener(queues = "user.new.location.mail.queue")
    public void receiveNewLocationMail(NewLocationEmailDTO newLocationDTO){
        simulateError();

        System.out.println("""
                Supposedly sending a new location mail to %s
                --------------------
                Username: %s 
                IP: %s
                """.formatted(
                newLocationDTO.email(),
                newLocationDTO.username(),
                newLocationDTO.userIp()
        ));
    }

    private void simulateError(){
        var random = new Random();
        var randomNum = random.nextInt(5);

        if(true)
            throw new RuntimeException("Mail provider error");
    }
}
