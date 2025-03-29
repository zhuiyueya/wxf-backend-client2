package top.xcyyds.wxfbackendclient.module.comment.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */
import java.util.List;

/**
 * ListChildCommentResponse
 */
@lombok.Data
public class ListChildCommentsResponse {
    /**
     * 评论列表
     */
    private List<SummaryComment> comments;
    /**
     * 此次响应的评论组的最后一条评论的创建时间，用于数据库中定位
     */
    private String timeCursor;
}