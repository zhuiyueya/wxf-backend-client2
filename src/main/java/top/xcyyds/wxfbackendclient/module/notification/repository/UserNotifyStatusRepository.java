package top.xcyyds.wxfbackendclient.module.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.UserNotifyStatus;

import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-13
 * @Description:
 * @Version:
 */

public interface UserNotifyStatusRepository extends JpaRepository<UserNotifyStatus, Long> {
    UserNotifyStatus findByUserInternalId(long userInternalId);
}
