package top.xcyyds.wxfbackendclient.module.notification.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:
 */
@Data
public class GetSubscriptionConfigResponse {
    private List<SummarySubscriptionConfig>configs;
}
