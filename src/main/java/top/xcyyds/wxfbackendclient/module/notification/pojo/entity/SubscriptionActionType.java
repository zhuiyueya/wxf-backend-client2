package top.xcyyds.wxfbackendclient.module.notification.pojo.entity;

import jakarta.persistence.*;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-11
 * @Description:
 * @Version:v1
 */
@lombok.Data
@Table
@Entity
public class SubscriptionActionType {
    /**
     * 订阅动作的唯一Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subscriptionActionTypeId;
    /**
     * 订阅的动作
     */
    private String action;

}

