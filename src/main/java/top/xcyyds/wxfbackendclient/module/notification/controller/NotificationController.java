package top.xcyyds.wxfbackendclient.module.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xcyyds.wxfbackendclient.common.Result;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.GetUserNotifyRequest;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.GetUserNotifyResponse;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.PullReminderRequest;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.PullReminderResponse;
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

    public Result<PullReminderResponse> pullReminder(Authentication authentication) {
        PullReminderRequest pullReminderRequest=new PullReminderRequest();
        pullReminderRequest.setUserPublicId(authentication.getPrincipal().toString());
        return Result.success(notificationService.pullReminder(pullReminderRequest));
    }

    @PostMapping("/userNotify/list")
    public Result<GetUserNotifyResponse> getUserNotify(Authentication authentication, @RequestBody GetUserNotifyRequest request) {
        request.setUserPublicId(authentication.getPrincipal().toString());
        return Result.success(notificationService.getUserNotify(request));
    }


}
