package top.xcyyds.wxfbackendclient.module.auth.pojo.denotation;

import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录类型绑定注解
 * 该注解用于标记不同的登录请求DTO类，将其与特定的登录类型进行绑定
 * 
 * 使用场景：
 * 1. 用于标注具体的登录请求DTO类（如手机号登录、邮箱登录等）
 * 2. 在运行时通过反射机制被识别，用于实现多态登录请求的自动类型映射
 * 
 * @Author: chasemoon
 * @CreateTime: 2025-03-14
 * @Description:
 * @Version:
 */
@Retention(RetentionPolicy.RUNTIME) // 注解在运行时可见，支持反射获取
@Target(ElementType.TYPE) // 注解只能应用于类型（类、接口等）
public @interface LoginTypeBinding {
    /**
     * 指定登录类型枚举值
     * @return 与该DTO类绑定的登录类型
     */
    LoginType value();
}
