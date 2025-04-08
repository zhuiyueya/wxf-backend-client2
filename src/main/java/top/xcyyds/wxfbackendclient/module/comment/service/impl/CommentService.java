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
    public AddChildCommentResponse addChildComment(AddChildCommentRequest addChildCommentRequest) {
        //指定为东八区时间（偏移量“+08：00"）
        OffsetDateTime beijingTime = OffsetDateTime.now(ZoneOffset.ofHours(8));
        //上传文件
        AddMediaAttachmentRequest addMediaAttachmentRequest=new AddMediaAttachmentRequest();
        addMediaAttachmentRequest.setTargetType(TargetType.COMMENT);
        addMediaAttachmentRequest.setMediaAttachment(addChildCommentRequest.getMediaAttachment());
        addMediaAttachmentRequest.setPublicId(addChildCommentRequest.getPublicId());
        MediaAttachment addMediaAttachmentResponse=mediaAttachmentService.addMediaAttachment(addMediaAttachmentRequest);
        //获取帖子
        Post post=postService.getPost(addChildCommentRequest.getPostId());

        //设置评论信息
        Comment comment=new Comment();
        comment.setContent(addChildCommentRequest.getContent());
        comment.setLikeCount(0);
        comment.setReplyCount(0);
        comment.setPublicId(addChildCommentRequest.getPublicId());
        comment.setReplyToUserPublicId(addChildCommentRequest.getReplyToUserPublicId());
        comment.setStatus(ContentState.PUBLISHED);
        comment.setCreateTime(beijingTime);
        comment.setMediaAttachment(addMediaAttachmentResponse);
        comment.setPost(post);
        comment.setParentCommentId(addChildCommentRequest.getParentCommentId());
        comment.setReplyToNickname(addChildCommentRequest.getReplyToNickName());

        comment=commentRepository.save(comment);
        return convertToAddChildCommentResponse(comment);
    }

    @Override
    public ListCommentsResponse listPostComments(ListCommentsRequest listCommentsRequest) {
        return null;
        //查询父评论
        List<Comment>parents=queryParentComments(listCommentsRequest);
        log.info("帖子{}的父评论数量{}",listCommentsRequest.getPostId(),parents.size());
        //批量获取父评论对应最新子评论
        Map<Long, Optional<Comment>> childCommentMap=queryLatestOneChildComments(parents);

        //构建二维结构
        List<List<SummaryComment>> structed=parents.stream().map(p->{
            List<SummaryComment>group=new ArrayList<>();
            group.add(convertToSummaryComment(p,true));
            childCommentMap.getOrDefault(p.getCommentId(), Optional.empty())
                    .ifPresent(child->
                            group.add(convertToSummaryComment(child,false))
                    );
            return group;
        }).collect(Collectors.toList());

        ListCommentsResponse listCommentsResponse=new ListCommentsResponse();
        listCommentsResponse.setComments(structed);
        return listCommentsResponse;
    }

    private Map<Long, Optional<Comment>> queryLatestOneChildComments(List<Comment> parents) {
    }

    private List<Comment> queryParentComments(ListCommentsRequest listCommentsRequest) {
    }

    private SummaryComment convertToSummaryComment(Comment comment, boolean isParent) {
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
    private AddChildCommentResponse convertToAddChildCommentResponse(Comment comment) {
        AddChildCommentResponse addChildCommentResponse=new AddChildCommentResponse();
        addChildCommentResponse.setCommentId(comment.getCommentId());
        addChildCommentResponse.setPostId(comment.getPost().getPostId());
        addChildCommentResponse.setContent(comment.getContent());
        addChildCommentResponse.setStatus(comment.getStatus());
        addChildCommentResponse.setCreateTime(comment.getCreateTime());
        addChildCommentResponse.setLikeCount(comment.getLikeCount());
        addChildCommentResponse.setReplyCount(comment.getReplyCount());
        addChildCommentResponse.setMediaAttachment(mediaAttachmentService.convertToSummaryMediaAttachment(comment.getMediaAttachment()));
        addChildCommentResponse.setAuthorInfo(userService.getSummaryAuthorInfoByPublicId(comment.getPublicId()));
        addChildCommentResponse.setReplyToNickname(comment.getReplyToNickname());
        addChildCommentResponse.setPublicId(comment.getPublicId());
        addChildCommentResponse.setParentCommentId(comment.getParentCommentId());

        return addChildCommentResponse;
    }
}
