package top.xcyyds.wxfbackendclient.module.post.pojo.dto;

import lombok.Data;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-18
 * @Description: 搜索帖子的请求体，对应于es的查询
 * @Version: v1
 */
@Data
public class SearchPostRequest {
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
     * 排序顺序（默认DESC）
     */
    private String sortOrder;
    /**
     * 上一次请求返回的帖子列表中最后一个帖子的createTime参数
     */
    private String timeCursor;
    /**
     * 用户publicId
     */
    private String userPublicId;
    /**
     * 搜索关键词
     */
    private String keyword;
}

