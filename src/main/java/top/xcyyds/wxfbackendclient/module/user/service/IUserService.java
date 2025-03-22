package top.xcyyds.wxfbackendclient.module.user.service;

import top.xcyyds.wxfbackendclient.module.user.pojo.dto.GetUserSelfInfoRequest;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.GetUserSelfInfoResponse;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-22
 * @Description:
 * @Version:
 */

public interface IUserService {
    GetUserSelfInfoResponse getUserSelfInfo(GetUserSelfInfoRequest request);
}
