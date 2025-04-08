package top.xcyyds.wxfbackendclient.module.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import top.xcyyds.wxfbackendclient.common.Result;
import top.xcyyds.wxfbackendclient.module.comment.pojo.dto.*;
import top.xcyyds.wxfbackendclient.module.comment.service.ICommentService;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-06
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    @Autowired
    @Qualifier("CommentService")  // 明确指定 Bean
    private ICommentService commentService;

    @PostMapping("/add/post")
    public Result<AddPostCommentResponse> addPostComment(Authentication authentication, @ModelAttribute AddPostCommentRequest addPostCommentRequest) {
        addPostCommentRequest.setPublicId(authentication.getPrincipal().toString());
        return Result.success(commentService.addPostComment(addPostCommentRequest));
    }

    @PostMapping("add/comment")
    public Result<AddChildCommentResponse> addChildComment(Authentication authentication, @ModelAttribute AddChildCommentRequest addChildCommentRequest) {
        addChildCommentRequest.setPublicId(authentication.getPrincipal().toString());
        return Result.success(commentService.addChildComment(addChildCommentRequest));
    }

    @PostMapping("/list/postComment")
    public Result<ListCommentsResponse> listPostComments(@RequestBody ListCommentsRequest listCommentsRequest) {
        return Result.success(commentService.listPostComments(listCommentsRequest));
    }

    @PostMapping("list/childComment")
    public Result<ListChildCommentsResponse> listChildComments(@RequestBody ListChildCommentsRequest listChildCommentsRequest) {
        return Result.success(commentService.listChildComments(listChildCommentsRequest));
    }
}
