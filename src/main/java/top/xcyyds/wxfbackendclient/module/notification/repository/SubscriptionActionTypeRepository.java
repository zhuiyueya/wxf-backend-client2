package top.xcyyds.wxfbackendclient.module.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.SubscriptionActionType;

import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:
 */

public interface SubscriptionActionTypeRepository extends JpaRepository<SubscriptionActionType, Long> {
    SubscriptionActionType findByActionName(String actionName);
}
