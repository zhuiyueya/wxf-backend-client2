package top.xcyyds.wxfbackendclient.module.user.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.User;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:v1
 */

public interface UserSchoolEnrollInfoRepository extends JpaRepository<User,Long> {

}
