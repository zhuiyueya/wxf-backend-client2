package top.xcyyds.wxfbackendclient.module.auth.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */

@lombok.Data
public class LoginResponse {
    /**
     * 是否为新注册用户（用于前端引导完善信息）
     */
    private boolean isNewUser;
    /**
     * 登录令牌（如JWT）,在后续请求中附加到header携带
     */
    private String token;
}
