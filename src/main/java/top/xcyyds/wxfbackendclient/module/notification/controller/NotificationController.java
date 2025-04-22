package top.xcyyds.wxfbackendclient.module.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import top.xcyyds.wxfbackendclient.common.Result;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.*;
import top.xcyyds.wxfbackendclient.module.notification.service.INotificationService;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:v1
 */
@RequestMapping("/api/v1/notification")
@RestController
public class NotificationController {
    @Autowired
    @Qualifier("NotificationService")
    private INotificationService notificationService;

    @GetMapping("/reminder/pull")
    public Result<PullReminderResponse> pullReminder(Authentication authentication) {
        PullReminderRequest pullReminderRequest=new PullReminderRequest();
        pullReminderRequest.setUserPublicId(authentication.getPrincipal().toString());
        return Result.success(notificationService.pullReminder(pullReminderRequest));
    }

    @PostMapping("/userNotify/list")
    public Result<ListUserNotifysResponse> listUserNotify(Authentication authentication, @RequestBody GetUserNotifyRequest request) {
        request.setUserPublicId(authentication.getPrincipal().toString());
        return Result.success(notificationService.listUserNotify(request));
    }
    @PostMapping("/create")
    public Result<CreateReminderResponse> createNotification(Authentication authentication, @RequestBody CreateReminderRequest request) {
        request.setSenderPublicId(authentication.getPrincipal().toString());
        return Result.success(notificationService.createReminder(request));
    }
    @GetMapping("/read")
    public Result<ReadUserNotifyResponse> readNotification(Authentication authentication) {
        ReadUserNotifyRequest readUserNotifyRequest=new ReadUserNotifyRequest();
        readUserNotifyRequest.setUserPublicId(authentication.getPrincipal().toString());
        return Result.success(notificationService.readUserNotify(readUserNotifyRequest));
    }
    @GetMapping("/subsrciptionConfig/get")
    public Result<GetSubscriptionConfigResponse> getUserNotifyConfig(Authentication authentication) {
        GetSubscriptionConfigRequest getSubscriptionConfigRequest=new GetSubscriptionConfigRequest();
        getSubscriptionConfigRequest.setUserPublicId(authentication.getPrincipal().toString());
        return Result.success(notificationService.getSubscriptionConfig(getSubscriptionConfigRequest));
    }

    @PatchMapping("/subscriptionConfig/update")
    public Result<UpdateSubscriptionConfigResponse> updateUserNotifyConfig(Authentication authentication, @RequestBody UpdateSubscriptionConfigRequest request) {
        request.setUserPublicId(authentication.getPrincipal().toString());
        return Result.success(notificationService.updateSubscriptionConfig(request));
    }

}
