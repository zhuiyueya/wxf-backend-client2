package top.xcyyds.wxfbackendclient.module.notification.pojo.entity;

import jakarta.persistence.*;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;
import top.xcyyds.wxfbackendclient.module.notification.pojo.enums.NotifyType;

import java.time.OffsetDateTime;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-11
 * @Description:
 * @Version:v1
 */
@Table
@Entity
@lombok.Data
public class Notify {
    /**
     * 通知消息的动作类型
     */
    @ManyToOne
    @JoinColumn(name="subscription_action_type_id")
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
     * 通知消息的主id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notifyId;
    /**
     * 通知消息的类型
     */
    private NotifyType notifyType;
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

