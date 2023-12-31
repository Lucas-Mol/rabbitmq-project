package com.mol.rabbitmqproject.userservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAMQPConfig {

    @Bean
    public FanoutExchange userRegisteredEventExchange() {
        return new FanoutExchange("user_registered_event_exchange");
    }

    @Bean
    public Queue mailUserRegisteredQueue() {
        return QueueBuilder
                    .nonDurable("mail.users.registered")
                    .deadLetterExchange("mail_users_registered_dlx")
                    .build();
    }

    @Bean
    public Binding bindingMailUserRegisteredQueue(Queue mailUserRegisteredQueue, FanoutExchange userRegisteredEventExchange) {
        return BindingBuilder.bind(mailUserRegisteredQueue).to(userRegisteredEventExchange);
    }

    @Bean
    public Queue scoreUserRegisteredQueue() {
        return QueueBuilder
                .nonDurable("score.users.registered")
                .build();
    }

    @Bean
    public Binding bindingScoreUserRegisteredQueue(Queue scoreUserRegisteredQueue, FanoutExchange userRegisteredEventExchange) {
        return BindingBuilder.bind(scoreUserRegisteredQueue).to(userRegisteredEventExchange);
    }

    @Bean
    public Queue mailNewLocationUserQueue() {
        return QueueBuilder
                .nonDurable("user.new.location.mail.queue")
                .build();
    }

    @Bean
    public Queue login2FASMSQueue() {
        return QueueBuilder
                .nonDurable("user.login.2fa.SMS.queue")
                .build();
    }
    @Bean
    public FanoutExchange mailUserRegisteredDlx() {
        return new FanoutExchange("mail_users_registered_dlx");
    }

    @Bean
    public Queue mailUserRegisteredDlq() {
        return QueueBuilder
                .nonDurable("mail.users.registered.dlq")
                .build();
    }

    @Bean
    public Binding bindingUserWelcomeEmailDlq(Queue mailUserRegisteredDlq, FanoutExchange mailUserRegisteredDlx) {
        return BindingBuilder.bind(mailUserRegisteredDlq).to(mailUserRegisteredDlx);
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory,
                                               Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        var rabbitTemplate =  new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);

        return rabbitTemplate;
    }
}
