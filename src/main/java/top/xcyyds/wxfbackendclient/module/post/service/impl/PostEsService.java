package top.xcyyds.wxfbackendclient.module.post.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.PostEsDocument;
import top.xcyyds.wxfbackendclient.module.post.repository.es.PostEsRepository;
import top.xcyyds.wxfbackendclient.module.post.service.IPostEsService;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-10
 * @Description: 帖子 Elasticsearch 服务实现类。该类实现了 IPostEsService 接口，封装了对 PostEsRepository 的调用，提供了帖子数据在 Elasticsearch 中索引、搜索、更新和删除等操作的具体业务逻辑。
 * @Version: v1
 */
@Service("PostEsService")
public class PostEsService implements IPostEsService {
    @Autowired
    private PostEsRepository postEsRepository;

    @Override
    public void addPostToEs(PostEsDocument post) {
        postEsRepository.save(post);
    }
}
