package top.xcyyds.wxfbackendclient.module.auth.pojo.dto;

import top.xcyyds.wxfbackendclient.module.auth.pojo.denotation.LoginTypeBinding;
import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */

@LoginTypeBinding(LoginType.WECHAT_LOGIN)//绑定登录枚举
@lombok.Data
public class WechatLoginRequest extends LoginRequest {
    /**
     * wx.login获取的临时code
     */
    private String code;

    @Override
    public LoginType getLoginType() {
        return null;
    }
}

