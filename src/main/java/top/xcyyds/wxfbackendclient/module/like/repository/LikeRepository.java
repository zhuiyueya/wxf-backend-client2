package top.xcyyds.wxfbackendclient.module.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.like.pojo.entity.Like;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;

import java.util.Optional;

/**
 * @Author: theManager
 * @CreateTime: 2025-04-08
 * @Description: 点赞数据访问层接口，负责操作 like 表
 * @Version: 1.0
 */
public interface LikeRepository extends JpaRepository<Like, Long> {



}
