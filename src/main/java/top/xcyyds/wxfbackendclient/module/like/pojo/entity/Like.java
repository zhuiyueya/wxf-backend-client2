package top.xcyyds.wxfbackendclient.module.like.pojo.entity;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */

import java.time.OffsetDateTime;

/**
 * like，点赞信息实体
 */
@lombok.Data
public class Like {
    /**
     * 点赞时间（ISO8601格式）
     */
    private OffsetDateTime createdAt;
    /**
     * 点赞记录唯一标识符（自增主键）
     */
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
    private long userPublicId;
}

