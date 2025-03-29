package top.xcyyds.wxfbackendclient.module.user.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.Major;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */


public interface MajorRepository extends JpaRepository<Major,Long> {

    Major findByMajorId(int majorId);
}
