package top.xcyyds.wxfbackendclient.module.notification.pojo.dto;

import lombok.Data;
import top.xcyyds.wxfbackendclient.module.notification.pojo.enums.NotifyType;

import java.time.OffsetDateTime;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:
 */
@Data
public class GetSubscriptionConfigRequest {
    private String userPublicId;
}
