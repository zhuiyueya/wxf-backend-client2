package top.xcyyds.wxfbackendclient.module.comment.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */
@lombok.Data
public class ListCommentsRequest {
    /**
     * 请求返回的最大父评论数量
     */
    private long pageSize;
    /**
     * 帖子ID
     */
    private long postId;
    /**
     * 上一次请求返回的响应中最后一个父评论的createTime，初次请求则设置为0
     */
    private String timeCursor;
}

