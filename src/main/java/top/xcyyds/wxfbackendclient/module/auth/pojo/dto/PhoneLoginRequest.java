package top.xcyyds.wxfbackendclient.module.auth.pojo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import top.xcyyds.wxfbackendclient.module.auth.pojo.denotation.LoginTypeBinding;
import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */

@LoginTypeBinding(LoginType.PHONE_LOGIN)
@lombok.Data
public class PhoneLoginRequest extends LoginRequest{
    /**
     * 验证码，验证码，需加密
     */
    @Valid
    @NotBlank(message = "验证码不能为空")
    private String authCode;
    /**
     * 手机号，手机号码，需加密
     */
    @Valid//显示激活校验
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")//校验手机号格式是否符合国内标准
    private String phone;

}

