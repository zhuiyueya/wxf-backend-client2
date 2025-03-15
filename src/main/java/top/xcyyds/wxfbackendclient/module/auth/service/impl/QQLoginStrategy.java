package top.xcyyds.wxfbackendclient.module.auth.service.impl;

import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.service.AbstractLoginStrategy;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.User;
import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-13
 * @Description:
 * @Version:
 */

public class QQLoginStrategy extends AbstractLoginStrategy {
    @Override
    public AuthenticationResult authenticate(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public boolean canSupportedLoginType(LoginType loginType) {
        return false;
    }
}
