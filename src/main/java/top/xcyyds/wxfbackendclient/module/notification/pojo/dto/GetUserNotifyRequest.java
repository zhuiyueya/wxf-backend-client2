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
public class GetUserNotifyRequest {
    /**
     * 为空表示都返回
     */
    private NotifyType notifyType;
    /**
     * 最多返回的通知数
     */
    private long pageSize;
    /**
     * 时间游标，指获取早于该时间创建的通知
     */
    private String timeCursor;
    /**
     * 用户公开ID
     */
    private String userPublicId;
}
