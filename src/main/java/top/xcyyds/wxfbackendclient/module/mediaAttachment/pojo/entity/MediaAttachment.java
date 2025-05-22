package top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import top.xcyyds.wxfbackendclient.common.ContentState;
import top.xcyyds.wxfbackendclient.module.comment.pojo.entity.Comment;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.enums.MediaType;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;

import java.time.OffsetDateTime;

/**
 * mediaAttachment
 */
@Table
@Entity
@lombok.Data
public class MediaAttachment {
    /**
     * 附件唯一标识符（自增主键）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachId;
    /**
     * SHA-256文件哈希值（模拟64位Hex）
     */
    private String fileHash;
    /**
     * 文件大小（字节，图片1KB-10MB，视频1MB-1GB）
     */
    private Long fileSize;
    /**
     * 媒体类型代码
     */
    private MediaType mediaType;
    /**
     * 上传者用户ID（与posts.public_id一致）
     */
    private String publicId;
    /**
     * 软删除标记（0-正常，1-已删除）
     */
    private ContentState status;
    /**
     * CDN存储路径（随机图片/视频URL）
     */
    private String storagePath;
    /**
     * 所属帖子ID（关联posts.post_id）
     */
    private Long targetId;
    /**
     * 附件所属的内容类型
     */
    private TargetType targetType;
    /**
     * 上传时间戳（晚于帖子创建时间）
     */
    private OffsetDateTime uploadTime;

    @ManyToOne
    @JoinColumn(name="post_id")
    @JsonBackReference//标记为从端，不进行序列化，即搜索时不再对应的另一张表
    private Post post;

    @OneToOne
    @JoinColumn(name = "comment_id")
    @JsonBackReference//标记为从端，不进行序列化，即搜索时不再对应的另一张表
    private Comment comment;

    // 在帖子id生成后异步调用该函数来设置targetId
    @PrePersist
    private void syncTargetId() {
        if (this.post != null) {
            this.targetId = this.post.getPostId();
        }
    }
}
