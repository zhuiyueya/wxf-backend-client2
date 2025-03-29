package top.xcyyds.wxfbackendclient.module.comment.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */
import java.util.List;

/**
 * ListCommentResponse
 */
@lombok.Data
public class ListCommentsResponse {
    /**
     * 评论列表，包含多个二元数组，即这是一个二维数组，但第二层数组大小只能为1或2
     */
    private List<List<SummaryComment>> comments;
}
