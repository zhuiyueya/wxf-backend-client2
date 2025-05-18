package top.xcyyds.wxfbackendclient.module.post.service;

import top.xcyyds.wxfbackendclient.module.post.pojo.entity.PostEsDocument;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-10
 * @Description:  帖子 Elasticsearch 服务接口。该接口定义了与帖子数据在 Elasticsearch 索引中进行交互的核心操作，包括但不限于帖子的索引、搜索、更新和删除等功能的契约。
 * @Version: v1
 */

public interface IPostEsService {
    void addPostToEs(PostEsDocument post);
}
