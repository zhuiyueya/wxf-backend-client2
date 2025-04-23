package top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-07
 * @Description:
 * @Version:
 */
@Data
public class AddMediaAttachmentRequest<T> {
    private MultipartFile mediaAttachment;
    private TargetType targetType;
    private T target;
    private String publicId;
}
