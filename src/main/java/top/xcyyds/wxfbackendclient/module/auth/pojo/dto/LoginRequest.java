package top.xcyyds.wxfbackendclient.module.auth.pojo.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

/**
 * 
 * @Author: chasemoon
 * @CreateTime: 2025-03-13
 * @Description:
 * 登录请求的抽象基类
 * 该类作为所有具体登录请求DTO的父类，实现了基于JSON的多态序列化和反序列化
 *
 * 设计说明：
 * 1. 使用Jackson的多态类型处理机制，通过loginType字段区分不同的登录请求类型
 * 2. 所有具体的登录请求DTO都需要继承此类并实现getLoginType方法
 * 3. 配合@LoginTypeBinding注解实现自动类型注册
 *
 * @JsonTypeInfo 配置说明：
 * - use=NAME: 使用类型名称作为类型标识
 * - property="loginType": 使用loginType字段存储类型信息
 * - visible=true: 类型字段在反序列化后可见
 * - include=EXISTING_PROPERTY: 类型信息存储在已有的属性中
 * @Version:v1
 */

@JsonTypeInfo(
        use=JsonTypeInfo.Id.NAME,
        include=JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "loginType",
        visible=true
)
public abstract class LoginRequest {
    /**
     * 登录类型字段
     * 用于在JSON序列化和反序列化时标识具体的登录请求类型
     */
    @NotNull(message = "登录类型不能为空")
    public LoginType loginType;

    // 添加 Getter 和 Setter
    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
