package top.xcyyds.wxfbackendclient.module.post.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-31
 * @Description:
 * @Version:
 */

import org.springframework.web.multipart.MultipartFile;
import top.xcyyds.wxfbackendclient.module.post.pojo.enums.PostType;

import java.util.List;

@lombok.Data
public class AddPostRequest {
    /**
     * 用户publicId
     */
    private String publicId;
    /**
     * 帖子内容
     */
    private String content;
    /**
     * 媒体文件列表
     */
    private List<MultipartFile> mediaAttachments;
    /**
     * 帖子类型
     */
    private PostType postType;
}

