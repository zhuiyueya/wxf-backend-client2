package top.xcyyds.wxfbackendclient.module.comment.service.impl;

import org.springframework.stereotype.Service;
import top.xcyyds.wxfbackendclient.module.comment.pojo.dto.*;
import top.xcyyds.wxfbackendclient.module.comment.service.ICommentService;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-06
 * @Description:
 * @Version:v1
 */
@Service("CommentService")
public class CommentService implements ICommentService {
    @Override
    public AddPostCommentResponse addPostComment(AddPostCommentRequest addPostCommentRequest) {
        return null;
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
}
