package top.xcyyds.wxfbackendclient.module.like.pojo.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.like.pojo.entity.pojo.entity.Like;
import top.xcyyds.wxfbackendclient.module.like.pojo.entity.pojo.enums.TargetType;

import java.util.List;
import java.util.Optional;

/**
 * @Author: theManager
 * @CreateTime: 2025-04-08
 * @Description: 点赞数据访问层接口，负责操作 like 表
 * @Version: 1.0
 */
public interface LikeRepository extends JpaRepository<Like, Long> {

    /**
     * 查询用户是否已经对某个对象点赞
     * @return 如果存在点赞记录，返回 Optional<Like>，否则返回 Optional.empty()
     */
    Optional<Like> findByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);
}
