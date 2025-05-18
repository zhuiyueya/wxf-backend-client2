package top.xcyyds.wxfbackendclient.module.post.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.PostEsDocument;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-17
 * @Description:
 * @Version:
 */

public interface PostEsRepository extends ElasticsearchRepository<PostEsDocument, String> {
}
