package top.xcyyds.wxfbackendclient.module.user.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xcyyds.wxfbackendclient.common.Result;
import top.xcyyds.wxfbackendclient.module.user.pojo.dto.GetUserSelfInfoResponse;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-22
 * @Description:接收前端请求并进行参数校验，之后调用Service进行业务处理
 * @Version:v1
 */

@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    /*
     * @Description: 获取用户自己的个人信息
     * @param Authentication 用户token认证返回信息
     * @return: Result<GetUserSelfInfoResponse>
     * @Author:  chasemoon
     * @date:  2025/3/22 15:48
     */

    @GetMapping("/me")
    public Result<GetUserSelfInfoResponse>getUserSelfInfo(Authentication authentication){
        return null;
    }
}
