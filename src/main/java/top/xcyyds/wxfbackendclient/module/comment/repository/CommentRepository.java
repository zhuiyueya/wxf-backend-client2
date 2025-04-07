package top.xcyyds.wxfbackendclient.module.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.comment.pojo.entity.Comment;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-06
 * @Description:
 * @Version:v1
 */

public interface CommentRepository extends JpaRepository<Comment, Long>
{

}
