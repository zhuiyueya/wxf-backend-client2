package top.xcyyds.wxfbackendclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-17
 * @Description:
 * @Version:
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = {"top.xcyyds.wxfbackendclient.module.post.repository.es","top.xcyyds.wxfbackendclient.module.user.persistence.repository.es"})
public class DataConfig {
    // 其他数据源或模板配置（如 ElasticsearchRestTemplate）
}
