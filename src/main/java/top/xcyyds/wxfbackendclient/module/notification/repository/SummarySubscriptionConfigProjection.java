package top.xcyyds.wxfbackendclient.module.notification.repository;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-21
 * @Description:
 * @Version:
 */

public interface SummarySubscriptionConfigProjection {
    Boolean getIsAllow();
    String getActionName();
    Long getConfigId();
}
