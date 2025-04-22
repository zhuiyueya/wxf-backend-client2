package top.xcyyds.wxfbackendclient.module.notification.pojo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.checkerframework.common.aliasing.qual.Unique;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-20
 * @Description:
 * @Version:
 */
@Data
@Entity
@Table
public class SubscriptionConfigMetaData {
    /**
     * 订阅配置核心数据
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long configId;
    /**
     * 订阅类型
     */
    @ManyToOne
    @JoinColumn(name="action",unique = true)
    private SubscriptionActionType action;
    /**
     * 默认是否允许
     */
    private boolean defaultAllow;

}
