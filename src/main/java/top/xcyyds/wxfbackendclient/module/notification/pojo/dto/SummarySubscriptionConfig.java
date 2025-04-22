package top.xcyyds.wxfbackendclient.module.notification.pojo.dto;

import lombok.Data;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-20
 * @Description:
 * @Version:
 */
@Data
public class SummarySubscriptionConfig {
    private Boolean isAllow;
    private String actionName;
    private Long configId;
    public SummarySubscriptionConfig(long configId, String actionName, Boolean isAllow) {
        this.configId = configId;
        this.actionName = actionName;
        this.isAllow = isAllow;
    }

    public SummarySubscriptionConfig(Boolean isAllow, String actionName, Long configId) {
        this.isAllow = isAllow;
        this.actionName = actionName;
        this.configId = configId;
    }
}
