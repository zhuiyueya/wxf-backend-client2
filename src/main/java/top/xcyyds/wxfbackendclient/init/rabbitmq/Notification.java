package top.xcyyds.wxfbackendclient.init.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-17
 * @Description:
 * @Version:
 */
@Configuration
public class Notification {
    private static final String EXCHANGE_NAME = "notification";
    private static final String QUEUE_NAME = "reminder";

    @Bean
    public DirectExchange notificationExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue reminderQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    public Binding binding(Queue reminderQueue, DirectExchange notificationExchange) {
        return BindingBuilder.bind(reminderQueue).to(notificationExchange).with(QUEUE_NAME);
    }
}
