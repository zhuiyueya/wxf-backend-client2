package top.xcyyds.wxfbackendclient.module.notification.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.Notify;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-18
 * @Description:
 * @Version:
 */
@Component
public class ReminderProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendReminder(Long notifyId) {
        rabbitTemplate.convertAndSend("notification","reminder", notifyId.toString());
    }
}
