package top.xcyyds.wxfbackendclient.module.notification.pojo.entity;

import jakarta.persistence.*;
import top.xcyyds.wxfbackendclient.common.ContentState;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.User;

import java.time.OffsetDateTime;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-11
 * @Description:
 * @Version:v1
 */
@lombok.Data
@Entity
@Table
public class UserNotify {
    /**
     * 创建时间
     */
    private OffsetDateTime createdAt;
    /**
     * 关联的notify
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notify_id")
    private Notify notify;
    /**
     * 状态，已读，未读等
     */
    private ContentState state;
    /**
     * 关联的用户
     */
    private long userInternalId;
    /**
     * 用户消息通知表的id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userNotifyId;
}
