package top.xcyyds.wxfbackendclient.module.mediaAttachment.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.xcyyds.wxfbackendclient.module.comment.pojo.entity.Comment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.MediaAttachmentRepository;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.AddMediaAttachmentRequest;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.SummaryMediaAttachment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.UploadMediaResponse;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity.MediaAttachment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.service.IMediaAttachmentService;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-07
 * @Description:
 * @Version:
 */

@Service("MediaAttachmentService")
public class MediaAttachmentService implements IMediaAttachmentService {
    @Autowired
    private SelfOSSService selfOSSService;
    @Autowired
    private MediaAttachmentRepository mediaAttachmentRepository;
    @Override
    public MediaAttachment addMediaAttachment(AddMediaAttachmentRequest addMediaAttachmentRequest) {
        //指定为东八区时间（偏移量“+08：00"）
        OffsetDateTime beijingTime = OffsetDateTime.now(ZoneOffset.ofHours(8));

        //调用上传图片OSS服务
        UploadMediaResponse uploadMediaResponse=selfOSSService.uploadMedia(addMediaAttachmentRequest.getMediaAttachment(),"/pic");

        //设置字段
        MediaAttachment newMediaAttachment=new MediaAttachment();

        newMediaAttachment.setTargetType(addMediaAttachmentRequest.getTargetType());
        newMediaAttachment.setPublicId(addMediaAttachmentRequest.getPublicId());
        newMediaAttachment.setStoragePath(uploadMediaResponse.getMediaPath());
        newMediaAttachment.setUploadTime(beijingTime);
        newMediaAttachment.setFileSize(0L);
        switch(addMediaAttachmentRequest.getTargetType()){
            case POST: {
                newMediaAttachment.setPost((Post) addMediaAttachmentRequest.getTarget());
                newMediaAttachment.setTargetId(((Post) addMediaAttachmentRequest.getTarget()).getPostId());
                break;
            }
            case COMMENT: {
                newMediaAttachment.setComment((Comment) addMediaAttachmentRequest.getTarget());
                newMediaAttachment.setTargetId(((Comment) addMediaAttachmentRequest.getTarget()).getCommentId());
                break;
            }
            default:
                break;
        }
        //通过在post表和comment表中配置级联保存操作来保存对于的帖子
        //return mediaAttachmentRepository.save(newMediaAttachment);
        return newMediaAttachment;
    }

    @Override
    public SummaryMediaAttachment convertToSummaryMediaAttachment(MediaAttachment mediaAttachment) {
        SummaryMediaAttachment summaryMediaAttachment = new SummaryMediaAttachment();

        BeanUtils.copyProperties(mediaAttachment, summaryMediaAttachment);
        return summaryMediaAttachment;
    }
}
