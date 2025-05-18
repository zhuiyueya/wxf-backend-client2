package top.xcyyds.wxfbackendclient.module.user.service.impl;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xcyyds.wxfbackendclient.module.user.persistence.repository.es.SummaryAuthorInfoEsRepository;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.SummaryAuthorInfoEsDocument;

import java.io.IOException;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-18
 * @Description:
 * @Version:
 */

@Slf4j
@Component
public class UserConsumer {
    @Autowired
    private SummaryAuthorInfoEsRepository summaryAuthorInfoEsRepository;
    private final ObjectMapper objectMapper;
    @Autowired
    public UserConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @RabbitListener(queues = "flink.output.queue.user")
    public void consumeSummaryUser(Message message, Channel channel) {
        log.info("收到消息：{}", new String(message.getBody()));
        try {
            SummaryAuthorInfoEsDocument summaryAuthorInfoEsDocument=objectMapper.readValue(message.getBody(), SummaryAuthorInfoEsDocument.class);
            summaryAuthorInfoEsRepository.save(summaryAuthorInfoEsDocument);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
