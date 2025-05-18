package top.xcyyds.wxfbackendclient.module.post.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.PostEsDocument;
import top.xcyyds.wxfbackendclient.module.post.service.IPostEsService;

import java.util.stream.Collectors;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-17
 * @Description:
 * @Version:
 */
@Slf4j
@Component
public class PostConsumer {
    @Autowired
    @Qualifier("PostEsService")
    private IPostEsService postEsService;

    private final ObjectMapper objectMapper;

    //注入已经配置好时间处理的Bean，也就是JacksonConfig类里注册的Bean，如果直接在函数内new一个，那么他是没有配置过JavaTimeModule的，就会导致无法反序列化
    @Autowired
    public PostConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
//        log.info("Injected ObjectMapper type: {}", objectMapper.getClass().getName());
//        log.info("Registered modules on injected ObjectMapper: {}",
//                 objectMapper.getRegisteredModuleIds().stream()
//                         .map(Object::toString)
//                         .collect(Collectors.joining(", ")));
    }

    @RabbitListener(queues = "flink.output.queue")
    public void comsumePost(Message message, Channel channel){
//        log.info("comsumePost using ObjectMapper type: {}", objectMapper.getClass().getName());
//        log.info("Registered modules on ObjectMapper in comsumePost: {}",
//                 objectMapper.getRegisteredModuleIds().stream()
//                         .map(Object::toString)
//                         .collect(Collectors.joining(", ")));

        log.info("收到消息：{}",new String(message.getBody()));
        try {
            PostEsDocument postEsDocument = objectMapper.readValue(message.getBody(), PostEsDocument.class);
            log.info("消息转换成功：{}",postEsDocument);
            postEsService.addPostToEs(postEsDocument);
        }catch (Exception e){
            log.error("消费消息失败",e);
            throw new RuntimeException(e);
        }
    }
}
