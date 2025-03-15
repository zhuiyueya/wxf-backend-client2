package top.xcyyds.wxfbackendclient.module.auth.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginResponse;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.PhoneLoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.WechatLoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.service.IAuthService;
import top.xcyyds.wxfbackendclient.module.auth.service.LoginStrategy;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.User;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.UserAuth;
import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;
import top.xcyyds.wxfbackendclient.util.IdGenerator;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService implements IAuthService {


    private final List<LoginStrategy> strategys;


    /*
     * @Description: 微信登录的Service,处理登录请求
     * @param WechatLoginRequest
     * @return: LoginResponse
     * @Author:  chasemoon
     * @date:  2025/3/12 08:53
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        return
                strategys.stream()
                        .filter(strategy->strategy.canSupportedLoginType(request.getLoginType()))
                        .findFirst()
                        .map(strategy->strategy.login(request))
                        .orElseThrow(()->new IllegalArgumentException("Unsupported login method :" + request.getClass().getName()));
    }


}
