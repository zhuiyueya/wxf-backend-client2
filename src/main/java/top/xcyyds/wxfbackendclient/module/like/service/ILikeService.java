package top.xcyyds.wxfbackendclient.module.like.service;

import top.xcyyds.wxfbackendclient.module.like.pojo.dto.AddUserLikeRequest;
import top.xcyyds.wxfbackendclient.module.like.pojo.dto.GetLikeInfoResponse;
import top.xcyyds.wxfbackendclient.module.like.pojo.dto.GetUserLikeResponse;

/**
 * @Author: theManager
 * @CreateTime: 2025-04-08
 * @Description:
 * @Version:
 */

public interface ILikeService {
    GetLikeInfoResponse getUserLike(String userId,GetUserLikeResponse getUserLikeResponse);

    GetLikeInfoResponse addUserLike(String userId,AddUserLikeRequest addUserLikeRequest);
}
