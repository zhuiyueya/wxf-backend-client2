package top.xcyyds.wxfbackendclient.module.auth.service;

import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginResponse;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.PhoneLoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.WechatLoginRequest;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.User;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */

public interface IAuthService {
    LoginResponse login(LoginRequest request);

}
