package top.xcyyds.wxfbackendclient.module.post.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */

import java.util.List;

/**
 * ListPostsResponse
 */
@lombok.Data
public class ListPostsResponse {
    /**
     * 此次响应返回的帖子列表中最后一个帖子的createTime参数
     */
    private String timeCursor;
    /**
     * 当前页的帖子数量
     */
    private Long pageSize;
    /**
     * 帖子列表
     */
    private List<SummaryPost> posts;
    /**
     * 总帖子数
     */
    private Long totalPosts;
}

