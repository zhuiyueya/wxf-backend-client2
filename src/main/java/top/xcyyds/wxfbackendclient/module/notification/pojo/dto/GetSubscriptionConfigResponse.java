package top.xcyyds.wxfbackendclient.module.notification.pojo.dto;

import lombok.Data;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:
 */
@Data
public class GetSubscriptionConfigResponse {
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
    private String notifyType;
    /**
     * 发送者的id
     */
    private String senderPublicId;
    /**
     * 状态，已读，未读等
     */
    private String state;
    /**
     * 目标Id
     */
    private long targetId;
    /**
     * 通知消息关联的对象类型
     */
    private String targetType;
    /**
     * 用户消息通知表的id
     */
    private long userNotifyId;
}
