package top.xcyyds.wxfbackendclient.config;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-21
 * @Description:
 * @Version:
 */

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Configuration
public class ElasticsearchConfig {

    @Autowired
    private RestClient restClient; // 注入 Spring Boot 自动配置的 RestClient

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        JacksonJsonpMapper jacksonJsonpMapper = new JacksonJsonpMapper(objectMapper);

        RestClientTransport transport = new RestClientTransport(restClient, jacksonJsonpMapper);
        return new ElasticsearchClient(transport);
    }
//
//    //使用微秒级别
//    @Bean
//    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
//        return new ElasticsearchCustomConversions(
//                List.of(new OffsetDateTimeToNanosConverter())
//        );
//    }
//
//    @WritingConverter
//    static class OffsetDateTimeToNanosConverter implements GenericConverter {
//        @Override
//        public Set<ConvertiblePair> getConvertibleTypes() {
//            return Set.of(new ConvertiblePair(OffsetDateTime.class, String.class));
//        }
//
//        @Override
//        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
//            OffsetDateTime dateTime = (OffsetDateTime) source;
//            return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"));
//        }
//    }
}