package top.xcyyds.wxfbackendclient.module.notification.pojo.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-14
 * @Description:
 * @Version:
 */
@Data
public class CreateSubscriptionActionTypeResponse {
    /**
     * 订阅动作的唯一Id
     */
    private long subscriptionActionTypeId;
    /**
     * 订阅的动作
     */
    private String action;
    /**
     * 订阅动作的描述
     */
    private String description;
    /**
     * 订阅动作的显示模板
     */
    private String displayTemplate;
}
