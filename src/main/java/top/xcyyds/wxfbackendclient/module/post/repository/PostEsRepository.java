package top.xcyyds.wxfbackendclient.module.post.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.PostEsDocument;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-10
 * @Description: Spring Data Elasticsearch Repository 接口，用于操作帖子 (PostEsDocument) 在 Elasticsearch 中的索引，提供 CRUD (创建、读取、更新、删除) 和搜索功能，供 Service 层调用。
 * @Version: v1
 */

public interface PostEsRepository extends ElasticsearchRepository<PostEsDocument,Long> {
}
