package top.xcyyds.wxfbackendclient.config;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import top.xcyyds.wxfbackendclient.module.auth.pojo.denotation.LoginTypeBinding;
import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

import java.util.Set;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-14
 * @Description:登录类型动态注册模块
 *  继承自Jackson的SimpleModule，用于处理登录请求的多态类型注册
 *
 *  主要功能：
 *  1. 自动扫描指定包下的所有带有@LoginTypeBinding注解的类
 *  2. 将这些类注册到Jackson的类型系统中，支持多态序列化
 *  3. 实现登录请求DTO的动态类型注册
 *
 *  工作原理：
 *  1. 使用Reflections库扫描指定包路径
 *  2. 识别带有@LoginTypeBinding注解的类
 *  3. 将类与注解中指定的LoginType建立映射关系
 *  4. 注册到Jackson的类型系统中实现自动类型转换
 * @Version:v1
 */

public class LoginTypeModule extends SimpleModule {

    /**
     * 要扫描的包路径
     * 通过配置文件注入，用于指定需要扫描的登录请求DTO所在的包
     */
    @Value("${jackson.modules.login-type-dto-package}")
    private String package2Scan;

    /**
     * 模块设置方法，在模块初始化时被调用
     * 
     * 实现功能：
     * 1. 扫描指定包下所有带有@LoginTypeBinding注解的类
     * 2. 提取注解中的LoginType值
     * 3. 将类和LoginType的映射关系注册到Jackson
     * 
     * @param context 设置上下文，用于注册子类型
     */

    @Override
    public void setupModule(SetupContext context) {
        // 创建反射工具，指定扫描的包路径
        Reflections reflections = new Reflections("top.xcyyds.wxfbackendclient.module.auth.pojo.dto");
        // 获取所有带有LoginTypeBinding注解的类
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(LoginTypeBinding.class);
        // 遍历所有类，注册到Jackson的类型系统
        types.forEach(clazz -> {
            // 获取注解中指定的登录类型
            LoginType type = clazz.getAnnotation(LoginTypeBinding.class).value();
            // 注册类型映射关系
            context.registerSubtypes(new NamedType(clazz, type.name()));
        });
    }
}
