package top.xcyyds.wxfbackendclient.module.notification.pojo.dto;

import jakarta.persistence.*;
import lombok.Data;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.SubscriptionActionType;
import top.xcyyds.wxfbackendclient.module.notification.pojo.enums.NotifyType;

import java.time.OffsetDateTime;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:
 */
@Data
public class CreateReminderRequest {
    /**
     * 通知消息的动作类型
     */
    private String action;
    /**
     * 发送者的id
     */
    private String senderPublicId;
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
}
