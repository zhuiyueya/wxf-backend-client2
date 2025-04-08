package top.xcyyds.wxfbackendclient.module.mediaAttachment.service;

import org.springframework.web.multipart.MultipartFile;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.AddMediaAttachmentRequest;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.SummaryMediaAttachment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity.MediaAttachment;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-07
 * @Description:
 * @Version:
 */

public interface IMediaAttachmentService {

    MediaAttachment addMediaAttachment(AddMediaAttachmentRequest addMediaAttachmentRequest);

    SummaryMediaAttachment convertToSummaryMediaAttachment(MediaAttachment mediaAttachment);
}
