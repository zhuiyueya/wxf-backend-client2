package top.xcyyds.wxfbackendclient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**

 * @Author: chasemoon
 * @CreateTime: 2025-03-14
 * @Description:
 * Jackson JSON配置类
 * 负责配置全局的JSON序列化和反序列化行为
 *
 * 主要功能：
 * 1. 创建并配置全局ObjectMapper实例
 * 2. 注册自定义的LoginTypeModule，实现登录请求多态类型的处理
 *
 * 设计说明：
 * - 通过Spring的@Configuration注解标记为配置类
 * - 使用@Bean注解提供全局ObjectMapper实例
 * - 集成自定义LoginTypeModule实现登录类型的动态注册
 * @Version:v1
 */

@Configuration
public class JacksonConfig {
    /**
     * 配置全局ObjectMapper实例
     * 
     * 功能说明：
     * 1. 创建新的ObjectMapper实例
     * 2. 注册LoginTypeModule以支持登录请求的多态类型处理
     * 3. 通过Spring容器管理ObjectMapper实例
     * 
     * @return 配置完成的ObjectMapper实例
     */

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 注册自定义的 LoginTypeModule 到 objectMapper 中
        objectMapper.registerModule(new LoginTypeModule());
        return objectMapper;
    }

}
