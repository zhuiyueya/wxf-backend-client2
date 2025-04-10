package top.xcyyds.wxfbackendclient.module.like.pojo.dto;

import lombok.Data;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;

/**
 * @Author: theManager
 * @CreateTime: 2025-04-08
 * @Description:查询用户是否点赞的数据流对象
 * @Version:
 */
@Data
public class GetUserLikeRequest {
    private long targetId;
    private TargetType targetType;
}
