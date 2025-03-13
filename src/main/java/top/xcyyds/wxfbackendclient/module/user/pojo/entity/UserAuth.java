package top.xcyyds.wxfbackendclient.module.user.pojo.entity;

import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */

public class UserAuth {
    /**
     * 关联的用户ID
     */
    private long userInternalId;
    /**
     * 登录方式类型
     */
    private LoginType authMethod;
    /**
     * 唯一标识（如openid/加密手机号）
     */
    private String authKey;
}
