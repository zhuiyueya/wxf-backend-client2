package top.xcyyds.wxfbackendclient.module.auth.pojo.dto;

import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-13
 * @Description:
 * @Version:
 */

public class StudentIdLoginRequest extends LoginRequest {
    @Override
    public LoginType getLoginType() {
        return null;
    }
}
