package top.xcyyds.wxfbackendclient.module.notification.pojo.dto;

import jakarta.persistence.*;
import lombok.Data;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.SubscriptionActionType;

import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:
 */
@Data
public class SubscribeRequest {
    /**
     * 订阅的目标的动作
     */
    private List<SubscriptionActionType> actions;
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
