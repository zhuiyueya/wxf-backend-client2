package top.xcyyds.wxfbackendclient.module.post.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */

import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.SummaryMediaAttachment;
import top.xcyyds.wxfbackendclient.module.post.pojo.enums.PostType;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.SummaryAuthorInfo;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * summaryPost
 */
@lombok.Data
public class SummaryPost {
    /**
     * 作者信息
     */
    private SummaryAuthorInfo authorInfo;
    /**
     * 收藏数量
     */
    private double collectCount;
    /**
     * 内容摘要（Markdown格式）
     */
    private String content;
    /**
     * 发布时间（UTC时间）
     */
    private OffsetDateTime createTime;
    /**
     * 是否包含多媒体
     */
    private boolean hasMedia;
    /**
     * 用于获取帖子列表是判断此次获取是否获取了完整的信息，若是则在进入帖子详细页时可以不用请求
     */
    @JsonProperty("isComplete")
    private boolean isComplete;
    /**
     * 点赞量
     */
    private double likeCount;
    /**
     * 帖子唯一标识
     */
    private double postId;
    /**
     * 回复数量
     */
    private double replyCount;
    /**
     * 分享数量
     */
    private double shareCount;
    /**
     * 媒体附件部分信息列表
     */
    private List<SummaryMediaAttachment> summaryMediaAttachment;
    /**
     * 附件总数
     */
    private double totalAttachments;
    /**
     * 浏览量
     */
    private double viewCount;
    /**
     * 帖子类型
     */
    private PostType postType;
}
