package top.xcyyds.wxfbackendclient.module.auth.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */


public class LoginResponse {

    /**
     * 登录令牌（如JWT）,在后续请求中附加到header携带
     */
    private String token;

    /**
     * 是否为新注册用户（用于前端引导完善信息）
     */

    private boolean isNewUser;

    @JsonProperty(value="isNewUser")
    public boolean isNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(boolean newUser) {
        isNewUser = newUser;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
