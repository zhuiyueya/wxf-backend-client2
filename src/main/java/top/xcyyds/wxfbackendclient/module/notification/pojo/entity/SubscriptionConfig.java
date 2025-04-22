package top.xcyyds.wxfbackendclient.module.notification.pojo.entity;

import jakarta.persistence.*;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.User;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-11
 * @Description:
 * @Version:v1
 */
@Entity
@Table
@lombok.Data
public class SubscriptionConfig {
    /**
     * 订阅配置id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subscriptionConfigId;
    /**
     * 订阅类型
     */
//    @ManyToOne
//    @JoinColumn(name="subscription_action_type_id")
//    private SubscriptionActionType action;
    /**
     * 是否允许
     */
    private boolean isAllow;
    /**
     * 绑定的用户
     */
    private long userInternalId;
    /**
     * 对应的配置元数据的id
     */
    private long subscriptionConfigMetaDataId;

}