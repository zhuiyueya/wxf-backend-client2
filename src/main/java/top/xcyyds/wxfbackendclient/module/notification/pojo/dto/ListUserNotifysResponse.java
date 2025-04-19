package top.xcyyds.wxfbackendclient.module.notification.pojo.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-13
 * @Description:
 * @Version:
 */
@Data
public class ListUserNotifysResponse {
    private List<GetUserNotifyResponse> notifies;
    private long pageSize;
    private OffsetDateTime timeCursor;
}
