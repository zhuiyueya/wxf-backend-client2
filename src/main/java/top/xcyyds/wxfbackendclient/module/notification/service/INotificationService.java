package top.xcyyds.wxfbackendclient.module.notification.service;

import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.*;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:v1
 */

public interface INotificationService {

    /*
     * 获取用户通知
     */
    public GetUserNotifyResponse getUserNotify(GetUserNotifyRequest request);

    /*
     * 拉取用户的通知到UserNotify
     */
    public PullReminderResponse pullReminder(PullReminderRequest request);

    /*
     * 创建提醒
     */
    public CreateReminderResponse createReminder(CreateReminderRequest request);

    /*
     * 创建提醒订阅
     */
    public SubscribeResponse subscribe(SubscribeRequest request);

    /*
     * 取消提醒订阅
     */
    public CancelSubscriptionResponse cancelSubscription(CancelSubscriptionRequest request);

    /*
     * 更新订阅配置
     */
    public UpdateSubscriptionConfigResponse updateSubscriptionConfig(UpdateSubscriptionConfigRequest request);

    /*
     * 获取订阅配置
     */
    public GetSubscriptionConfigResponse getSubscriptionConfig(GetSubscriptionConfigRequest request);

    /*
     * 设置用户通知为已读
     */
    public ReadUserNotifyResponse readUserNotify(ReadUserNotifyRequest request);
}
