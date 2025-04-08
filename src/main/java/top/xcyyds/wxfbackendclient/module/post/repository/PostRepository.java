package top.xcyyds.wxfbackendclient.module.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Specification<Post> spec, Pageable pageable);

    Post findByPostId(Long postId);
}
