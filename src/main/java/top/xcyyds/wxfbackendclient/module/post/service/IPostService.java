package top.xcyyds.wxfbackendclient.module.post.service;

import top.xcyyds.wxfbackendclient.module.post.pojo.dto.AddPostRequest;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.ListPostsRequest;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.ListPostsResponse;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.SummaryPost;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */

public interface IPostService {
    SummaryPost getPostDetail(String postId);

    ListPostsResponse listPosts(ListPostsRequest request);

    SummaryPost addPost(AddPostRequest request);

    Post getPost(long postId);
}
