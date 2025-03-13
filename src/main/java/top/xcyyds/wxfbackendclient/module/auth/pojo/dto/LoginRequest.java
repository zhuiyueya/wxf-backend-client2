package top.xcyyds.wxfbackendclient.module.auth.pojo.dto;

import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-13
 * @Description:
 * @Version:
 */

public abstract class LoginRequest {
    LoginType loginType;
    public abstract LoginType getLoginType();
}
