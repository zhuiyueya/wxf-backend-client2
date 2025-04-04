package top.xcyyds.wxfbackendclient.module.post.service;

import top.xcyyds.wxfbackendclient.module.post.pojo.dto.AddPostRequest;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.ListPostsRequest;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.ListPostsResponse;
import top.xcyyds.wxfbackendclient.module.post.pojo.dto.SummaryPost;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */

public interface IPostService {
    SummaryPost getPost(String postId);

    ListPostsResponse listPosts(ListPostsRequest request);

    SummaryPost addPost(AddPostRequest request);
}
