package top.xcyyds.wxfbackendclient.module.notification.pojo.entity;

import jakarta.persistence.*;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-11
 * @Description:
 * @Version:v1
 */
@Entity
@Table
@lombok.Data
public class Subscription {
    /**
     * 订阅的目标的动作
     */
    @ManyToMany()
    @JoinTable(
            name = "subscription_actions",
            joinColumns = @JoinColumn(name = "subscription_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_action_type_id")
    )
    private List<SubscriptionActionType> actions;
    /**
     * 创建时间
     */
    private OffsetDateTime createdAt;
    /**
     * 订阅id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subscriptionId;
    /**
     * 订阅的目标
     */
    private long targetId;
    /**
     * 通知消息关联的对象类型
     */
    private TargetType targetType;
    /**
     * 关联的用户id
     */
    private long userInternalId;
}
