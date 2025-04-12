package top.xcyyds.wxfbackendclient.module.notification.service.impl;

import org.springframework.stereotype.Service;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.*;
import top.xcyyds.wxfbackendclient.module.notification.service.INotificationService;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:v1
 */
@Service("NotificationService")
public class NotificationService implements INotificationService {
    @Override
    public GetUserNotifyResponse getUserNotify(GetUserNotifyRequest request) {
        return null;
    }

    @Override
    public PullReminderResponse pullReminder(PullReminderRequest request) {
        return null;
    }

    @Override
    public CreateReminderResponse createReminder(CreateReminderRequest request) {
        return null;
    }

    @Override
    public SubscribeResponse subscribe(SubscribeRequest request) {
        return null;
    }

    @Override
    public CancelSubscriptionResponse cancelSubscription(CancelSubscriptionRequest request) {
        return null;
    }

    @Override
    public UpdateSubscriptionConfigResponse updateSubscriptionConfig(UpdateSubscriptionConfigRequest request) {
        return null;
    }

    @Override
    public GetSubscriptionConfigResponse getSubscriptionConfig(GetSubscriptionConfigRequest request) {
        return null;
    }

    @Override
    public ReadUserNotifyResponse readUserNotify(ReadUserNotifyRequest request) {
        return null;
    }
}
