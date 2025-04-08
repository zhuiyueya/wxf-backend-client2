package top.xcyyds.wxfbackendclient.module.user.pojo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: theManager
 * @CreateTime: 2025-04-06
 * @Description:
 * @Version:
 */
@Data
public class UpdateUserSelfAvatarRequest {
    private String publicId;
    private MultipartFile multipartFile;
}
