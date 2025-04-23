package top.xcyyds.wxfbackendclient.module.notification.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import top.xcyyds.wxfbackendclient.common.ContentState;
import top.xcyyds.wxfbackendclient.init.rabbitmq.Notification;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.Notify;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.Subscription;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.UserNotify;
import top.xcyyds.wxfbackendclient.module.notification.repository.UserNotifyRepository;
import top.xcyyds.wxfbackendclient.module.notification.service.INotificationService;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-18
 * @Description:
 * @Version:
 */

@Slf4j
@Component
public class ReminderConsumer {
    @Autowired
    @Qualifier("NotificationService")
    private INotificationService notificationService;


    @RabbitListener(queues="reminder")
    public void consumeReminder(Message message, Channel channel) throws IOException {
        log.debug("consumeReminder: " + new String(message.getBody()));

        Notify notify=notificationService.getNotifyByNotifyId(Long.parseLong(new String(message.getBody())));
        List<Long> subscriptionUserInternalIds=notificationService.getSubscriptionUserInternalIdsByNotify(notify);
        for(long subscriptionUserInternalId:subscriptionUserInternalIds){

            //指定为东八区时间（偏移量“+08：00"）
            OffsetDateTime beijingTime = OffsetDateTime.now(ZoneOffset.ofHours(8));

            UserNotify userNotify=new UserNotify();
            userNotify.setNotify(notify);
            userNotify.setCreatedAt(beijingTime);
            userNotify.setState(ContentState.PUBLISHED);
            userNotify.setUserInternalId(subscriptionUserInternalId);

            notificationService.saveUserNotify(userNotify);
            notificationService.updateUserNotifyStatus(1,0,0,subscriptionUserInternalId);
        }

    }
}
