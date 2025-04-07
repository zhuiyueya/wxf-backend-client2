package top.xcyyds.wxfbackendclient.module.comment.pojo.entity;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */

import jakarta.persistence.*;
import top.xcyyds.wxfbackendclient.common.ContentState;

import java.time.OffsetDateTime;

/**
 * comment
 */
@lombok.Data
@Table
@Entity
public class Comment {
    /**
     * 评论的唯一标识符，自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论创建时间（ISO8601格式）
     */
    private OffsetDateTime createTime;
    /**
     * 点赞统计
     */
    private long likeCount;
    /**
     * 父评论ID（自关联），NULL表示顶级评论
     */
    private Long parentCommentId;
    /**
     * 关联的帖子ID，外键引用posts表的post_id
     */
    private long postId;
    /**
     * 发表评论的用户ID
     */
    private String publicId;
    /**
     * 子评论数量统计
     */
    private long replyCount;
    /**
     * 用于回复子评论时显示回复的是谁
     */
    private String replyToNickname;
    /**
     * 评论状态：0-正常，1-已删除，2-审核中
     */
    private ContentState status;
    /**
     * 被回复的用户ID（UUID格式），用于回复评论/帖子的消息通知
     */
    private String replyToUserPublicId;
}

