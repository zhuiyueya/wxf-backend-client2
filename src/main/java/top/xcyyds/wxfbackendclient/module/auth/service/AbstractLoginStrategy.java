package top.xcyyds.wxfbackendclient.module.auth.service;

import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginResponse;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.User;
import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-13
 * @Description:
 * @Version:
 */

public abstract class AbstractLoginStrategy implements LoginStrategy {

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }
    public abstract User authenticate(LoginRequest loginRequest);

    public String generateToken(String subject) {
        return null;
    }

    @Override
    public abstract boolean canSupportedLoginType(LoginType loginType) ;
}
