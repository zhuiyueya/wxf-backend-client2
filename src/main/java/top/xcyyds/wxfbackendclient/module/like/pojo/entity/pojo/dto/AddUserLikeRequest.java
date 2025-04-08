package top.xcyyds.wxfbackendclient.module.like.pojo.entity.pojo.dto;

import lombok.Data;
import top.xcyyds.wxfbackendclient.module.like.pojo.entity.pojo.enums.TargetType;

/**
 * @Author: theManager
 * @CreateTime: 2025-04-08
 * @Description:用户添加点赞的数据流对象
 * @Version:
 */
@Data
public class AddUserLikeRequest {
    private long targetId;
    private TargetType targetType;
}
