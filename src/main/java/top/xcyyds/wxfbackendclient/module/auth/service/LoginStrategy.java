package top.xcyyds.wxfbackendclient.module.auth.service;

import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginResponse;
import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-13
 * @Description:
 * @Version:
 */

public interface LoginStrategy {
    LoginResponse login(LoginRequest loginRequest);
    boolean canSupportedLoginType(LoginType loginType);
}
