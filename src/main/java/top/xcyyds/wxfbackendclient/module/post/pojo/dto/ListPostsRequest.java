package top.xcyyds.wxfbackendclient.module.post.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */

/**
 * ListPostsRequest
 */
@lombok.Data
public class ListPostsRequest {
    /**
     * 请求返回的最大帖子数量
     */
    private long pageSize;
    /**
     * 过滤帖子类型
     */
    private String postType;
    /**
     * 排序字段（默认CREATE_TIME_SORTED）
     */
    private String sortType;
    /**
     * 上一次请求返回的帖子列表中最后一个帖子的createTime参数
     */
    private String timeCursor;
}
