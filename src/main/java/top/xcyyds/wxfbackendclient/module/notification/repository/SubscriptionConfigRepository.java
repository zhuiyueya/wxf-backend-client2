package top.xcyyds.wxfbackendclient.module.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.SubscriptionActionType;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.SubscriptionConfig;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:
 */

public interface SubscriptionConfigRepository extends JpaRepository<SubscriptionConfig, Long> {
    SubscriptionConfig findByUserInternalIdAndAction(long userInternalId, SubscriptionActionType action);
}
