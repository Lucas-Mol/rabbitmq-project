package com.mol.rabbitmqproject.smsservice.amqp;

import com.mol.rabbitmqproject.smsservice.dto.UserLoggedInDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UserListener {

    @RabbitListener(queues = "user.login.2fa.SMS.queue")
    public void receive2FASMS(UserLoggedInDTO userLoggedIn){
        simulateError();

        System.out.println("""
                Supposedly sending SMS to %s
                --------------------
                Phone Number: %s 
                Token sent: %s
                """.formatted(
                        userLoggedIn.username(),
                        userLoggedIn.smsPhoneNumber(),
                        userLoggedIn.token()
                ));
    }

    private void simulateError(){
        var random = new Random();
        var randomNum = random.nextInt(5);

        if(randomNum == 3)
            throw new RuntimeException("SMS provider error");
    }
}
