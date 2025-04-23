package top.xcyyds.wxfbackendclient.module.notification.pojo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-13
 * @Description:
 * @Version:
 */
@Data
@Table
@Entity
public class UserNotifyStatus {
    @Id
    private long userInternalId;
//    private OffsetDateTime lastPullNotifyTime;
//    private OffsetDateTime currentPullNotifyTime;
    private long totalNotifyVersion;
    private long readNotifyVersion;
    private long totalUnreadCount;
}
