package top.xcyyds.wxfbackendclient.module.like.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.xcyyds.wxfbackendclient.common.ContentState;
import top.xcyyds.wxfbackendclient.module.comment.pojo.entity.Comment;
import top.xcyyds.wxfbackendclient.module.comment.repository.CommentRepository;
import top.xcyyds.wxfbackendclient.module.like.pojo.dto.AddUserLikeRequest;
import top.xcyyds.wxfbackendclient.module.like.pojo.dto.GetLikeInfoResponse;
import top.xcyyds.wxfbackendclient.module.like.pojo.dto.GetUserLikeRequest;
import top.xcyyds.wxfbackendclient.module.like.pojo.entity.Like;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;
import top.xcyyds.wxfbackendclient.module.like.service.ILikeService;
import top.xcyyds.wxfbackendclient.module.like.repository.LikeRepository;
import top.xcyyds.wxfbackendclient.module.post.repository.PostRepository;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;
import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-22
 * @Description:实现用户相关业务逻辑
 * @Version:v1
 */
@Transactional
@Slf4j
@Service
public class LikeService implements ILikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public GetLikeInfoResponse getUserLike(String userId, GetUserLikeRequest getUserLikeRequest) {
        long targetId = getUserLikeRequest.getTargetId();
        TargetType targetType = getUserLikeRequest.getTargetType();

        GetLikeInfoResponse response = new GetLikeInfoResponse();
        boolean isLiked = likeRepository.existsByUserPublicIdAndTargetIdAndTargetType(userId, targetId, targetType);
        if(isLiked){
            Like like = likeRepository.findByUserPublicIdAndTargetIdAndTargetType(userId, targetId, targetType)
            .orElseThrow();
            response.setLikeId(like.getLikeId());
        }else {
            response.setLikeId(-1);
        }
        response.setStatus(isLiked ? ContentState.DELETED : ContentState.PUBLISHED);

        // 根据类型获取点赞数
        long likeCount = 0;
        if (targetType == TargetType.POST) {
            Post post = postRepository.findByPostId(targetId);
            likeCount = post.getLikeCount();
        } else if (targetType == TargetType.COMMENT) {
            Optional<Comment> optionalComment = commentRepository.findByCommentId(targetId);
            Comment comment = optionalComment.get();
            likeCount = comment.getLikeCount();
        }
        response.setLikeCount(likeCount);
        return response;
    }

    @Override
    public GetLikeInfoResponse addUserLike(String userId, AddUserLikeRequest addUserLikeRequest){
        // 从请求中获取目标信息
        try {
            long targetId = addUserLikeRequest.getTargetId();
            TargetType targetType = addUserLikeRequest.getTargetType();

            // 检查是否已点赞
            boolean hasLiked = likeRepository.existsByUserPublicIdAndTargetIdAndTargetType(userId, targetId, targetType);
            GetLikeInfoResponse response = new GetLikeInfoResponse();
            response.setStatus(hasLiked ? ContentState.PUBLISHED : ContentState.DELETED);

            long likeCount = 0;
            Post post = null;
            Comment comment = null;

            // 先根据类型更新点赞数
            if (targetType == TargetType.POST) {
                post = postRepository.findByPostId(targetId);
                likeCount = post.getLikeCount();
            } else if (targetType == TargetType.COMMENT) {
                Optional<Comment> optionalComment = commentRepository.findByCommentId(targetId);
                comment = optionalComment.get();
                likeCount = comment.getLikeCount();
            }

            // 根据点赞状态更新点赞数
            if (hasLiked) {
                //更新点赞数
                likeCount--;
                // 存在则删除实体
                log.info("1");
                likeRepository.deleteByUserPublicIdAndTargetIdAndTargetType(userId, targetId, targetType);
                log.info("1");
                response.setLikeId(-1);
            } else {
                likeCount++;
                // 不存在则增加实体
                Like like = new Like();
                like.setUserPublicId(userId);
                like.setTargetId(targetId);
                like.setTargetType(targetType);
                if (targetType == TargetType.POST) {
                    like.setPost(post);
                } else if (targetType == TargetType.COMMENT) {
                    like.setComment(comment);
                }
                ;
                like.setCreatedAt(OffsetDateTime.now());
                Like savedLike = likeRepository.save(like);
                response.setLikeId(savedLike.getLikeId());
            }
            response.setLikeCount(likeCount);

            // 更新点赞数到对应实体
            if (targetType == TargetType.POST && post != null) {
                post.setLikeCount(likeCount);
                postRepository.save(post);
            } else if (targetType == TargetType.COMMENT && comment != null) {
                comment.setLikeCount(likeCount);
                commentRepository.save(comment);
            }

            return response;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
