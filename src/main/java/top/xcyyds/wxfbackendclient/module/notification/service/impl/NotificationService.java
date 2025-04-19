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
        Notify notify=new Notify();
        //指定为东八区时间（偏移量“+08：00"）
        OffsetDateTime beijingTime = OffsetDateTime.now(ZoneOffset.ofHours(8));
        SubscriptionActionType subscriptionActionType=getSubscriptionActionType(request.getAction());
        notify.setAction(subscriptionActionType);
        notify.setTargetId(request.getTargetId());
        notify.setTargetType(request.getTargetType());
        notify.setNotifyType(NotifyType.REMINDER);
        notify.setSenderPublicId(request.getSenderPublicId());
        notify.setCreatedAt(beijingTime);
        notify.setSourceId(request.getSourceId());
        notify.setSourceType(request.getSourceType());
        notify.setContent(subscriptionActionType.getDisplayTemplate());

        notifyRepository.save(notify);

        CreateReminderResponse response=new CreateReminderResponse();

        reminderProducer.sendReminder(notify.getNotifyId());
        return response;
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

    @Override
    public SubscriptionActionType getSubscriptionActionType(String actionName) {
        return subscriptionActionTypeRepository.findByActionName(actionName);
    }
}
