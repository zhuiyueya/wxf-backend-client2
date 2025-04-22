package top.xcyyds.wxfbackendclient.module.notification.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.xcyyds.wxfbackendclient.module.notification.pojo.dto.SummarySubscriptionConfig;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.SubscriptionActionType;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.SubscriptionConfig;

import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:
 */

public interface SubscriptionConfigRepository extends JpaRepository<SubscriptionConfig, Long> {
    //SubscriptionConfig findByUserInternalIdAndAction(long userInternalId, SubscriptionActionType action);

    List<SubscriptionConfig> findAllByUserInternalId(long internalId);

    SubscriptionConfig findByUserInternalIdAndSubscriptionConfigMetaDataId(long userInternalId, long subscriptionConfigMetaDataId);


}
