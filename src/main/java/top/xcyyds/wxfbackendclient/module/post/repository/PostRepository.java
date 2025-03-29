package top.xcyyds.wxfbackendclient.module.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */

public interface PostRepository extends JpaRepository<Post, Long> {
}
