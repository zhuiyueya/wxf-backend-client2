package top.xcyyds.wxfbackendclient.module.notification.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.*;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.*;
import top.xcyyds.wxfbackendclient.module.notification.pojo.enums.NotifyType;
import top.xcyyds.wxfbackendclient.module.notification.producer.ReminderProducer;
import top.xcyyds.wxfbackendclient.module.notification.repository.*;
import top.xcyyds.wxfbackendclient.module.notification.service.INotificationService;
import top.xcyyds.wxfbackendclient.module.post.service.IPostService;
import top.xcyyds.wxfbackendclient.module.user.service.impl.UserService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:v1
 */
@Slf4j
@Service("NotificationService")
public class NotificationService implements INotificationService {

    @Autowired
    private NotifyRepository notifyRepository;

    @Autowired
    private UserNotifyStatusRepository userNotifyStatusRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ReminderProducer reminderProducer;
    @Override
    public GetUserNotifyResponse getUserNotify(GetUserNotifyRequest request) {
        return null;
    }

    @Override
    public PullReminderResponse pullReminder(PullReminderRequest request) {
        long internalId= userService.getInternalIdByPublicId(request.getUserPublicId());
        UserNotifyStatus userNotifyStatus=updateUserNotifyStatus(0,1,0,internalId);

        PullReminderResponse pullReminderResponse=new PullReminderResponse();
        pullReminderResponse.setTotalUnreadCount(userNotifyStatus.getTotalUnreadCount());
        return pullReminderResponse;
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
        //将publicId转换为internalId
        long internalId= userService.getInternalIdByPublicId(request.getUserPublicId());
        updateUserNotifyStatus(0,0,1,internalId);

        ReadUserNotifyResponse response=new ReadUserNotifyResponse();

        return response;
    }

    @Override
    public SubscriptionActionType getSubscriptionActionType(String actionName) {
        return subscriptionActionTypeRepository.findByActionName(actionName);
    }
    @Override
    public UserNotifyStatus updateUserNotifyStatus(long totalVersionOffset, long readNotifyVersionOffset, long totalUnreadCount, long userInternalId) {
        UserNotifyStatus userNotifyStatus=userNotifyStatusRepository.findByUserInternalId(userInternalId);

        //用户初次使用找不到则进行创建
        if (userNotifyStatus==null){
            userNotifyStatus=new UserNotifyStatus();
            userNotifyStatus.setUserInternalId(userInternalId);
            userNotifyStatus.setTotalNotifyVersion(0);
            userNotifyStatus.setReadNotifyVersion(0);
            userNotifyStatus.setTotalUnreadCount(0);
        }
        //逻辑：创建时总的版本=总的版本+1，拉取时将总的版本-已读的版本数得到未读消息数，read消息时再将已读版本数+未读消息数，这样即使用户获取完未读数量但没点进去之间这段空余时间有新的消息也不会消失
        //或者说：应该在创建userNotidy时才将总版本+1，这样才能避免用户看到未读消息>1，但是点进去没有拉取到
        if (totalVersionOffset>0){
            userNotifyStatus.setTotalNotifyVersion(userNotifyStatus.getTotalNotifyVersion()+totalVersionOffset);
        }else if(readNotifyVersionOffset>0){
            userNotifyStatus.setTotalUnreadCount(userNotifyStatus.getTotalNotifyVersion()-userNotifyStatus.getReadNotifyVersion());
        }else if (totalUnreadCount>0){
            userNotifyStatus.setReadNotifyVersion(userNotifyStatus.getReadNotifyVersion()+userNotifyStatus.getTotalUnreadCount());
            userNotifyStatus.setTotalUnreadCount(userNotifyStatus.getTotalNotifyVersion()-userNotifyStatus.getReadNotifyVersion());
        }


        return userNotifyStatusRepository.save(userNotifyStatus);
    }


}
