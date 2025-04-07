package top.xcyyds.wxfbackendclient.module.post.pojo.entity;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.ToString;
import top.xcyyds.wxfbackendclient.module.comment.pojo.entity.Comment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity.MediaAttachment;
import top.xcyyds.wxfbackendclient.module.post.pojo.enums.PostType;

import java.time.OffsetDateTime;
import java.util.List;
@Table
@Entity
@lombok.Data
public class Post {
    /**
     * 收藏数统计（0-5000随机）
     */
    private Long collectCount;
    /**
     * 帖子正文内容（随机中文文本）
     */
    private String content;
    /**
     * 发布时间戳（最近30天内随机时间）
     */
    private OffsetDateTime createTime;
    /**
     * 是否包含多媒体文件（随机布尔值）
     */
    private Boolean hasMedia;
    /**
     * 点赞数统计（0-1万随机）
     */
    private Long likeCount;
    /**
     * 帖子唯一标识符（自增主键）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    /**
     * 发布者用户ID（UUID格式）
     */
    private String publicId;
    /**
     * 回复数统计（0-2000随机）
     */
    private Long replyCount;
    /**
     * 分享数量
     */
    private long shareCount;
    /**
     * 内容状态（0-正常, 1-待审核, 2-已删除）
     */
    private Long status;
    /**
     * 关联附件总数（0-10随机）
     */
    private Long totalAttachments;
    /**
     * 浏览次数统计（0-10万随机）
     */
    private Long viewCount;
    /**
     * 帖子类型
     */
    private PostType postType;
    /**
     * 关联的媒体附件表
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference//主端，正常序列化，找到绑定的另一张表的数据
    private List<MediaAttachment> mediaAttachments;
    /**
     * 关联的评论
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference//主端，正常序列化，找到绑定的另一张表的数据
    private List<Comment> comments;
}
