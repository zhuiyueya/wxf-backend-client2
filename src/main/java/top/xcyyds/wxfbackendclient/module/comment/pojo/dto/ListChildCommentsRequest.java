package top.xcyyds.wxfbackendclient.module.comment.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */
@lombok.Data
public class ListChildCommentsRequest {
    /**
     * 请求返回的最大子评论数量
     */
    private long pageSize;
    /**
     * 父评论ID
     */
    private long parentCommentId;
    /**
     * 上一次响应的二层评论组的最后一条评论的创建时间，用于数据库中定位
     */
    private String timeCursor;
}

