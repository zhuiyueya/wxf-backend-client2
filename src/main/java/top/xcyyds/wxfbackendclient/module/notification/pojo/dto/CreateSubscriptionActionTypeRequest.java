package top.xcyyds.wxfbackendclient.module.notification.pojo.dto;

import lombok.Data;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-14
 * @Description:
 * @Version:
 */
@Data
public class CreateSubscriptionActionTypeRequest {
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
