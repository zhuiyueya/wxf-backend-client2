package top.xcyyds.wxfbackendclient.module.comment.service;

import top.xcyyds.wxfbackendclient.module.comment.pojo.dto.*;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-06
 * @Description:
 * @Version:v1
 */

public interface ICommentService {
    AddPostCommentResponse addPostComment(AddPostCommentRequest addPostCommentRequest);

    AddChildCommentResponse addChildComment(AddChildCommentRequest addChildCommentRequest);

    ListCommentsResponse listPostComments(ListCommentsRequest listCommentsRequest);

    ListChildCommentsResponse listChildComments(ListChildCommentsRequest listChildCommentsRequest);
}
