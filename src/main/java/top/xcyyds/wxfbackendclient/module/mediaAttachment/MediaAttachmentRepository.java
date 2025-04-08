package top.xcyyds.wxfbackendclient.module.mediaAttachment;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity.MediaAttachment;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-07
 * @Description:
 * @Version:
 */

public interface MediaAttachmentRepository extends JpaRepository<MediaAttachment, Long> {
}
