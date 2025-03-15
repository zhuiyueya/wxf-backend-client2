package top.xcyyds.wxfbackendclient.module.auth.service.impl;

import org.springframework.stereotype.Service;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginResponse;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.PhoneLoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.WechatLoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.service.IAuthService;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */

@Service
public class AuthService implements IAuthService {

    /*
     * @Description: 微信登录的Service,处理登录请求
     * @param WechatLoginRequest
     * @return: LoginResponse
     * @Author:  chasemoon
     * @date:  2025/3/12 08:53
     */
    @Override
    public LoginResponse wechatLogin(WechatLoginRequest request) {
        return null;
    public LoginResponse login(LoginRequest request) {
    }


}
