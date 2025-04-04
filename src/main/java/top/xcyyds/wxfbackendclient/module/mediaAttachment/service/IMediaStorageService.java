package top.xcyyds.wxfbackendclient.module.mediaAttachment.service;

import org.springframework.web.multipart.MultipartFile;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.UploadMediaResponse;

import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-31
 * @Description:
 * @Version:
 */

public interface IMediaStorageService {
    public UploadMediaResponse uploadMedia(MultipartFile mediaAttachments,String subPath);
}
