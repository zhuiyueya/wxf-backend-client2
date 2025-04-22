package top.xcyyds.wxfbackendclient.module.comment.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Parameter;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.xcyyds.wxfbackendclient.common.ContentState;
import top.xcyyds.wxfbackendclient.module.comment.pojo.dto.*;
import top.xcyyds.wxfbackendclient.module.comment.pojo.entity.Comment;
import top.xcyyds.wxfbackendclient.module.comment.repository.CommentRepository;
import top.xcyyds.wxfbackendclient.module.comment.service.ICommentService;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto.AddMediaAttachmentRequest;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity.MediaAttachment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.service.IMediaAttachmentService;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.CreateReminderRequest;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.SubscribeRequest;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.SubscribeResponse;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.SubscriptionActionType;
import top.xcyyds.wxfbackendclient.module.notification.service.impl.NotificationService;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;
import top.xcyyds.wxfbackendclient.module.post.service.IPostService;
import top.xcyyds.wxfbackendclient.module.user.service.impl.UserService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-06
 * @Description:
 * @Version:v1
 */
@Slf4j
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

    @Autowired
    private  EntityManager entityManager;

    @Autowired
    private NotificationService notificationService;

    final List<String> subscribeActionTypeNames=List.of("REPLY","LIKE");

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

        //创建通知
        createReminder(comment,true);
        //订阅通知
        createSubscription(comment);

        return convertToAddPostCommentResponse(comment);
    }

    /*
     * 创建评论提醒消息
     */
    private void createReminder(Comment comment,Boolean isParent){
        CreateReminderRequest createReminderRequest=new CreateReminderRequest();
        createReminderRequest.setSenderPublicId(comment.getPublicId());
        createReminderRequest.setSourceType(TargetType.COMMENT);
        createReminderRequest.setSourceId(comment.getCommentId());
        String acyionName=isParent?"COMMENT":"REPLY";
        //回复帖子
        if(isParent){
            createReminderRequest.setTargetType(TargetType.POST);
            createReminderRequest.setTargetId(comment.getPost().getPostId());
        }else{//回复评论
            createReminderRequest.setTargetType(TargetType.COMMENT);
            createReminderRequest.setTargetId(comment.getParentCommentId());
        }

        createReminderRequest.setAction(acyionName);
        notificationService.createReminder(createReminderRequest);
    }

    private SubscribeResponse createSubscription(Comment comment){
        SubscribeRequest subscribeRequest=new SubscribeRequest();
        //to do:将comment保存的字段改为internalId
        long internalId=userService.getInternalIdByPublicId(comment.getPublicId());
        //将对应的actionType字符串转换成entity
        List<SubscriptionActionType> subscribeActionTypes=new ArrayList<>();
        for (String subscribeActionTypeName : subscribeActionTypeNames) {
            SubscriptionActionType subscribeActionType=notificationService.getSubscriptionActionType(subscribeActionTypeName);
            subscribeActionTypes.add(subscribeActionType);
        }

        subscribeRequest.setTargetType(TargetType.COMMENT);
        subscribeRequest.setTargetId(comment.getCommentId());
        subscribeRequest.setUserInternalId(internalId);
        subscribeRequest.setActions(subscribeActionTypes);

        SubscribeResponse subscribeResponse=notificationService.subscribe(subscribeRequest);
        return subscribeResponse;
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

        //创建通知
        createReminder(comment,false);
        //订阅通知
        createSubscription(comment);
        return convertToAddChildCommentResponse(comment);
    }

    @Override
    public ListCommentsResponse listPostComments(ListCommentsRequest listCommentsRequest) {
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
        if(parents.isEmpty()){
            return Collections.emptyMap();
        }

        String sql= """
                SELECT * FROM (
                    SELECT
                         *,
                         ROW_NUMBER() OVER(
                             PARTITION BY parent_comment_id
                             ORDER BY create_time DESC
                         ) AS rn
                    FROM comment
                    WHERE parent_comment_id IN (:parentIds)
                        AND status = :stateCode
                ) AS ranked
                WHERE rn = 1
                """;

        try {

            List<Comment> childs = entityManager.createNativeQuery(sql, Comment.class)
                    .setParameter("parentIds",
                            parents.stream().map(Comment::getCommentId).collect(Collectors.toList()))
                    .setParameter("stateCode", ContentState.PUBLISHED.getCode())
                    .getResultList();//getResultList会让查询数据库的操作开始执行


            return childs.stream()
                    .collect(Collectors.groupingBy(
                            Comment::getParentCommentId,
                            Collectors.reducing((a, b) -> a)
                    ));
        }catch (Exception e){
            throw new RuntimeException("查询子评论失败",e);
        }
    }

    private List<Comment> queryParentComments(ListCommentsRequest listCommentsRequest) {
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query=cb.createQuery(Comment.class);
        Root<Comment> root=query.from(Comment.class);

        List<Predicate> predicates=new ArrayList<>();
        predicates.add(cb.equal(root.get("post").get("postId"),listCommentsRequest.getPostId()));
        predicates.add(cb.isNull(root.get("parentCommentId")));
        predicates.add(cb.equal(root.get("status"),ContentState.PUBLISHED));

        //时间游标处理
        if(StringUtils.hasText(listCommentsRequest.getTimeCursor())){
            OffsetDateTime cursorTime=OffsetDateTime.parse(listCommentsRequest.getTimeCursor());
            predicates.add(cb.lessThan(root.get("createTime"),cursorTime));
        }

        query.where(predicates.toArray(new Predicate[0]))
                .orderBy(cb.desc(root.get("createTime")));

        return entityManager.createQuery(query)
                .setMaxResults((int)listCommentsRequest.getPageSize())
                .getResultList();
    }

    private SummaryComment convertToSummaryComment(Comment comment, boolean isParent) {
        SummaryComment summaryComment=new SummaryComment();
        summaryComment.setCommentId(comment.getCommentId());
        summaryComment.setPostId(comment.getPost().getPostId());
        summaryComment.setContent(comment.getContent());
        summaryComment.setStatus(comment.getStatus());
        summaryComment.setCreateTime(comment.getCreateTime());
        summaryComment.setLikeCount(comment.getLikeCount());
        summaryComment.setReplyCount(comment.getReplyCount());
        if (comment.getMediaAttachment()!=null)
            summaryComment.setMediaAttachment(mediaAttachmentService.convertToSummaryMediaAttachment(comment.getMediaAttachment()));
        summaryComment.setAuthorInfo(userService.getSummaryAuthorInfoByPublicId(comment.getPublicId()));
        if(comment.getReplyToNickname()!=null)
            summaryComment.setReplyToNickname(comment.getReplyToNickname());
        summaryComment.setReplyToUserPublicId(comment.getReplyToUserPublicId());
        if(isParent)
            summaryComment.setParentCommentId(comment.getParentCommentId());

        return summaryComment;
    }

    @Override
    public ListChildCommentsResponse listChildComments(ListChildCommentsRequest listChildCommentsRequest) {
        List<Comment>comments=queryChildComments(listChildCommentsRequest);
        return convertToListChildCommentsResponse(comments);
    }

    private List<Comment> queryChildComments(ListChildCommentsRequest listChildCommentsRequest) {
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query=cb.createQuery(Comment.class);
        Root<Comment> root=query.from(Comment.class);
        List<Predicate> predicates=new ArrayList<>();
        predicates.add(cb.equal(root.get("parentCommentId"),listChildCommentsRequest.getParentCommentId()));
        predicates.add(cb.equal(root.get("status"),ContentState.PUBLISHED));
        //时间游标处理
        if(StringUtils.hasText(listChildCommentsRequest.getTimeCursor())){
            OffsetDateTime cursorTime=OffsetDateTime.parse(listChildCommentsRequest.getTimeCursor());
            predicates.add(cb.lessThan(root.get("createTime"),cursorTime));
        }
        query.where(predicates.toArray(new Predicate[0])).orderBy(cb.desc(root.get("createTime")));
        return entityManager.createQuery(query)
                .setMaxResults((int)listChildCommentsRequest.getPageSize())
                .getResultList();
    }

    private ListChildCommentsResponse convertToListChildCommentsResponse(List<Comment> comments) {
        ListChildCommentsResponse listChildCommentsResponse=new ListChildCommentsResponse();
        if(comments.size()==0){
            return listChildCommentsResponse;
        }
        listChildCommentsResponse.setComments(comments.stream()
                .map(comment -> convertToSummaryComment(comment,false))
                .collect(Collectors.toList()));
        listChildCommentsResponse.setTimeCursor(comments.get(comments.size()-1).getCreateTime().toString());
        return listChildCommentsResponse;
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
