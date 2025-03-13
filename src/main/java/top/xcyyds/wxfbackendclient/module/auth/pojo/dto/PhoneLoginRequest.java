package top.xcyyds.wxfbackendclient.module.auth.pojo.dto;

import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */

@lombok.Data
public class PhoneLoginRequest extends LoginRequest{
    /**
     * 验证码，验证码，需加密
     */
    private String authCode;
    /**
     * 手机号，手机号码，需加密
     */
    private String phone;

    @Override
    public LoginType getLoginType() {
        return null;
    }

}

