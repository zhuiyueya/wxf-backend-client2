package top.xcyyds.wxfbackendclient.module.user.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.Department;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */

public interface DepartmentRepository extends JpaRepository<Department,Long> {


    Department findByDepartmentId(long departmentId);
}

