package top.xcyyds.wxfbackendclient.module.like.pojo.entity;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */

import jakarta.persistence.*;
import top.xcyyds.wxfbackendclient.common.ContentState;
import top.xcyyds.wxfbackendclient.module.comment.pojo.entity.Comment;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.User;

import java.time.OffsetDateTime;

/**
 * like，点赞信息实体
 */
@Table(name="likes")
@lombok.Data
@Entity
public class Like {
    /**
     * 点赞时间（ISO8601格式）
     */
    private OffsetDateTime createdAt;
    /**
     * 点赞记录唯一标识符（自增主键）
     */
    @Id
    private long likeId;
    /**
     * 被点赞的目标ID（帖子或评论ID）
     */
    private long targetId;
    /**
     * 目标类型：post-帖子，comment-评论
     */
    private TargetType targetType;
    /**
     * 执行点赞的用户ID
     */
    private String userPublicId;

    // 关联用户
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "internalId", insertable = false, updatable = false)
    private User user;

    // 关联帖子
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    private Post post;

    // 关联评论
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment;
}

