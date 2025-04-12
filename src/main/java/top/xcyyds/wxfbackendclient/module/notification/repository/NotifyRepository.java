package top.xcyyds.wxfbackendclient.module.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.notification.pojo.entity.Notify;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-12
 * @Description:
 * @Version:
 */

public interface NotifyRepository extends JpaRepository<Notify, Long> {
}
