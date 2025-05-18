package top.xcyyds.wxfbackendclient.module.user.persistence.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.SummaryAuthorInfoEsDocument;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-18
 * @Description:
 * @Version:
 */

public interface SummaryAuthorInfoEsRepository extends ElasticsearchRepository<SummaryAuthorInfoEsDocument, String> {
}
