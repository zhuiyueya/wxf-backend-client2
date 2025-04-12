package top.xcyyds.wxfbackendclient.module.notification.pojo.dto;

import top.xcyyds.wxfbackendclient.common.ContentState;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;
import top.xcyyds.wxfbackendclient.module.notification.pojo.enums.NotifyType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-11
 * @Description:
 * @Version:
 */
@lombok.Data
public class GetUserNotifyResponse {
    /**
     * 通知消息的动作类型
     */
    private String action;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建时间
     */
    private String createdAt;
    /**
     * 通知消息的类型
     */
    private NotifyType notifyType;
    /**
     * 发送者的id
     */
    private String senderPublicId;
    /**
     * 状态，已读，未读等
     */
    private ContentState state;
    /**
     * 目标Id
     */
    private long targetId;
    /**
     * 通知消息关联的对象类型
     */
    private TargetType targetType;
    /**
     * 用户消息通知表的id
     */
    private long userNotifyId;
}
