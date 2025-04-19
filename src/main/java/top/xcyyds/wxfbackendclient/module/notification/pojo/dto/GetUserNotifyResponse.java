package top.xcyyds.wxfbackendclient.module.notification.pojo.dto;

import top.xcyyds.wxfbackendclient.common.ContentState;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.SubscriptionActionType;
import top.xcyyds.wxfbackendclient.module.notification.pojo.enums.NotifyType;

import java.time.OffsetDateTime;

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
    private SubscriptionActionType action;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建时间
     */
    private OffsetDateTime createdAt;
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
     * 被通知源回复/点赞的id
     */
    private long targetId;
    /**
     * 被通知源回复/点赞的实体类型
     */
    private TargetType targetType;
    /**
     * 发起通知的源id
     */
    private long sourceId;
    /**
     * 发起通知的源类型
     */
    private TargetType sourceType;
    /**
     * 用户消息通知表的id
     */
    private long userNotifyId;
}
