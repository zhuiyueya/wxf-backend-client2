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
    @Column(unique = true)
    private String actionName;
    /**
     * 订阅动作的描述
     */
    private String description;
    /**
     * 订阅动作的显示模板
     */
    private String displayTemplate;
}
/*
    LIKE	点赞	用户对帖子/评论点赞	parent_type=评论, child_type=帖子
    COMMENT	评论	用户发表评论（含回复）	parent_type=帖子, child_type=评论
    REPLY	回复	用户回复他人评论	parent_type=评论, child_type=评论
    FOLLOW	关注	用户关注其他用户	parent_type=用户, child_type=用户
    MESSAGE	私信	用户发送私信	parent_type=用户, child_type=用户
    MENTION	@提及	用户在内容中@其他用户	parent_type=帖子, child_type=用户
    DELETE	删除	用户或管理员删除内容	parent_type=评论, child_type=帖子
    SYSTEM	系统通知	平台公告、规则更新等	无关联对象
 */

