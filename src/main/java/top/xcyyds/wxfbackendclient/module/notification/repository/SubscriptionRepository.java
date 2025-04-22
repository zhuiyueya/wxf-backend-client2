package top.xcyyds.wxfbackendclient.module.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.Subscription;

import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:
 */

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByTargetIdAndTargetType(long targetId, TargetType targetType);
}
