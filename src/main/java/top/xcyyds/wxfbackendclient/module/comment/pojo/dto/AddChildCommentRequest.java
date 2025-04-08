package top.xcyyds.wxfbackendclient.module.comment.pojo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-06
 * @Description:
 * @Version:v1
 */
@Data
public class AddChildCommentRequest {
    private long parentCommentId;
    private String content;
    private String publicId;
    private MultipartFile mediaAttachment;
    private String replyToNickName;
    /**
     * 被回复的用户ID（UUID格式），用于回复评论/帖子的消息通知
     */
    private String replyToUserPublicId;
    private long postId;
}
