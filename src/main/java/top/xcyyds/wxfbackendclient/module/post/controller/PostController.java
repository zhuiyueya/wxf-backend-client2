package top.xcyyds.wxfbackendclient.module.post.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import top.xcyyds.wxfbackendclient.common.Result;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.*;
import top.xcyyds.wxfbackendclient.module.post.service.impl.PostService;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @PostMapping("/list")
    public Result<ListPostsResponse> listPosts(@RequestBody ListPostsRequest request) {
        return Result.success(postService.listPosts(request));
    }
    @GetMapping("/get/{postId}")
    public Result<SummaryPost> getPost(@PathVariable long postId) {
        return Result.success(postService.getPostDetail(postId));
    }
    //curl -X POST "http://localhost:8080/api/v1/posts/add" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3NzczOGI2YjU3ZThhYjNhMDNlOWU4ZGY2MmY1MWJlOWMxZjVhMmRiM2M0YWQzNjNlNTI0NzU1OWFiYTc3NTg4IiwiaWF0IjoxNzQzNjg4MjQzLCJleHAiOjE3NDM3MjQyNDN9.nUMy6J-a685AkDrZrsagM0nW_VSPO3jbKSH3u5OhTEo" -F "content=This is a new post" -F "postType=HELP_POST" -F "mediaAttachments=@./中文.jpg"
    @PostMapping("/add")                                          //@ModelAttribute用于传统的表单提交，application/x-www-form-urlencoded 或 multipart/form-data  ，  @RequestBody​Content-Type 要求必须为 application/json 等 body 数据格式
    public Result<SummaryPost>addPost(Authentication authentication, @ModelAttribute AddPostRequest request) {
        request.setPublicId(authentication.getPrincipal().toString());

        return Result.success(postService.addPost(request));
    }
    @PostMapping("/search")
    public Result<ListPostsResponse> searchPosts(@RequestBody SearchPostRequest request) {
        return Result.success(postService.searchPosts(request));
    }
}
