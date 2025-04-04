package top.xcyyds.wxfbackendclient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

        // 配置ObjectMapper以支持Java 8日期时间API
        /*
         *Java 8 引入了新的日期和时间 API (java.time 包)，但 Jackson 默认并不支持这些新类型。为了使 Jackson 能够正确地序列化和反序列化 java.time 包下的类（如 OffsetDateTime），需要注册 JavaTimeModule 并禁用将日期时间类型写入时间戳的默认行为。
         */
        objectMapper.registerModule(new JavaTimeModule());//JavaTimeModule 是 Jackson 的一个模块，专门用于支持 Java 8 引入的新的日期和时间 API (java.time 包)。这个模块是由 Jackson 团队提供的，用于解决 Jackson 默认不支持 Java 8 日期和时间类型的问题。Java 8 引入了新的日期和时间 API，这些 API 更加现代化和强大，但 Jackson 在最初并不支持这些新类型。为了填补这一空白，Jackson 团队开发了 JavaTimeModule，并将其包含在 Jackson 的核心库中。通过注册这个模块，Jackson 就能够正确地处理 Java 8 的日期和时间类型了。
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);//SerializationFeature.WRITE_DATES_AS_TIMESTAMPS：这是 Jackson 的一个序列化特性，默认情况下，Jackson 会将日期和时间类型序列化为时间戳（即长整型数字）。禁用这个特性后，Jackson 会将日期和时间类型序列化为 ISO-8601 格式的字符串，这是更符合人类可读性和跨语言互操作性的格式。
        return objectMapper;
    }

}
