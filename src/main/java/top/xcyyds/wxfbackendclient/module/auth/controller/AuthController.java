package top.xcyyds.wxfbackendclient.module.auth.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xcyyds.wxfbackendclient.common.Result;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginResponse;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.PhoneLoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.WechatLoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.service.impl.AuthService;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    /*
     * @Description: 微信登录的Controller,接收前端微信登录请求，并调用业务逻辑
     * @param  WechatLoginRequest
     * @return: Result<LoginResponse>
     * @Author:  chasemoon
     * @date:  2025/3/12 08:49
     */
    @PostMapping("/login")
    public Result<LoginResponse> wechatLogin(@Valid @RequestBody LoginRequest request) {
        log.debug("wechatLogin request:{}", request);
        return Result.success(authService.login(request));
    }


}
