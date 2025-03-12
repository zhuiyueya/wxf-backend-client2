package top.xcyyds.wxfbackendclient.module.auth.service;

import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginResponse;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.PhoneLoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.WechatLoginRequest;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */

public interface IAuthService {
    LoginResponse wechatLogin(WechatLoginRequest request);
    LoginResponse phoneLogin(PhoneLoginRequest request);
}
