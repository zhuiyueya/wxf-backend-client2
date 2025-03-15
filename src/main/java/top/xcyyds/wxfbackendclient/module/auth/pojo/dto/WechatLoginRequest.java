package top.xcyyds.wxfbackendclient.module.auth.pojo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import top.xcyyds.wxfbackendclient.module.auth.pojo.denotation.LoginTypeBinding;
import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */

@LoginTypeBinding(LoginType.WECHAT_LOGIN)//绑定登录枚举
@Data
public class WechatLoginRequest extends LoginRequest {
    /**
     * wx.login获取的临时code
     */
    @Valid
    @NotBlank
    private String code;

    public WechatLoginRequest() {
        this.loginType = LoginType.WECHAT_LOGIN;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

