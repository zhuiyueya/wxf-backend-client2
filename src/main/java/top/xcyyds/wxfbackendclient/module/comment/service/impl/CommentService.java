package top.xcyyds.wxfbackendclient.module.comment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.xcyyds.wxfbackendclient.common.ContentState;
import top.xcyyds.wxfbackendclient.module.comment.pojo.dto.*;
import top.xcyyds.wxfbackendclient.module.comment.pojo.entity.Comment;
import top.xcyyds.wxfbackendclient.module.comment.repository.CommentRepository;
import top.xcyyds.wxfbackendclient.module.comment.service.ICommentService;
import top.xcyyds.wxfbackendclient.module.like.pojo.entity.TargetType;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.AddMediaAttachmentRequest;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity.MediaAttachment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.service.IMediaAttachmentService;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;
import top.xcyyds.wxfbackendclient.module.post.service.IPostService;
import top.xcyyds.wxfbackendclient.module.user.service.impl.UserService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-06
 * @Description:
 * @Version:v1
 */
@Service("CommentService")
public class CommentService implements ICommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    @Qualifier("MediaAttachmentService")
    private IMediaAttachmentService mediaAttachmentService;
    @Autowired
    @Qualifier("PostService")
    private IPostService postService;
    @Autowired
    private UserService userService;

    @Override
    public AddPostCommentResponse addPostComment(AddPostCommentRequest addPostCommentRequest) {
        //指定为东八区时间（偏移量“+08：00"）
        OffsetDateTime beijingTime = OffsetDateTime.now(ZoneOffset.ofHours(8));
        //上传文件
        AddMediaAttachmentRequest addMediaAttachmentRequest=new AddMediaAttachmentRequest();
        addMediaAttachmentRequest.setTargetType(TargetType.COMMENT);
        addMediaAttachmentRequest.setMediaAttachment(addPostCommentRequest.getMediaAttachment());
        addMediaAttachmentRequest.setPublicId(addPostCommentRequest.getPublicId());
        MediaAttachment addMediaAttachmentResponse=mediaAttachmentService.addMediaAttachment(addMediaAttachmentRequest);
        //获取帖子
        Post post=postService.getPost(addPostCommentRequest.getPostId());

        //设置评论信息
        Comment comment=new Comment();
        comment.setContent(addPostCommentRequest.getContent());
        comment.setLikeCount(0);
        comment.setReplyCount(0);
        comment.setPublicId(addPostCommentRequest.getPublicId());
        comment.setReplyToUserPublicId(addPostCommentRequest.getReplyToUserPublicId());
        comment.setStatus(ContentState.PUBLISHED);
        comment.setCreateTime(beijingTime);
        comment.setMediaAttachment(addMediaAttachmentResponse);
        comment.setPost(post);

        comment=commentRepository.save(comment);
        return convertToAddPostCommentResponse(comment);
    }

    @Override
    public AddChildCommentResponse addChildComment(AddPostCommentRequest addChildCommentRequest) {
        return null;
    }

    @Override
    public ListCommentsResponse listPostComments(ListCommentsRequest listCommentsRequest) {
        return null;
    }

    @Override
    public ListChildCommentsResponse listChildComments(ListChildCommentsRequest listChildCommentsRequest) {
        return null;
    }

    private AddPostCommentResponse convertToAddPostCommentResponse(Comment comment) {
        AddPostCommentResponse addPostCommentResponse=new AddPostCommentResponse();
        addPostCommentResponse.setCommentId(comment.getCommentId());
        addPostCommentResponse.setPostId(comment.getPost().getPostId());
        addPostCommentResponse.setContent(comment.getContent());
        addPostCommentResponse.setStatus(comment.getStatus());
        addPostCommentResponse.setCreateTime(comment.getCreateTime());
        addPostCommentResponse.setLikeCount(comment.getLikeCount());
        addPostCommentResponse.setReplyCount(comment.getReplyCount());
        addPostCommentResponse.setMediaAttachment(mediaAttachmentService.convertToSummaryMediaAttachment(comment.getMediaAttachment()));
        addPostCommentResponse.setAuthorInfo(userService.getSummaryAuthorInfoByPublicId(comment.getPublicId()));
        return addPostCommentResponse;
    }
}
