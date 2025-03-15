package top.xcyyds.wxfbackendclient.module.user.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.UserAuth;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */

public interface UserAuthRepository extends JpaRepository<UserAuth,Long> {

    UserAuth findByAuthKey(String authKey);
}
