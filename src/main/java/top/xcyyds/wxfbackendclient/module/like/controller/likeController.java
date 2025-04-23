package top.xcyyds.wxfbackendclient.module.like.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import top.xcyyds.wxfbackendclient.common.Result;
import top.xcyyds.wxfbackendclient.module.like.pojo.dto.AddUserLikeRequest;
import top.xcyyds.wxfbackendclient.module.like.pojo.dto.GetLikeInfoResponse;
import top.xcyyds.wxfbackendclient.module.like.pojo.dto.GetUserLikeRequest;
import top.xcyyds.wxfbackendclient.module.like.service.impl.LikeService;

/**
 * @Author: theManager
 * @CreateTime: 2025-04-09
 * @Description:
 * @Version:
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/like")
public class likeController {
    @Autowired
    private LikeService likeService;

    @PatchMapping("/addLike")
    public Result<GetLikeInfoResponse> updateUserSelfLike(Authentication authentication, @RequestBody AddUserLikeRequest addUserLikeRequest) {
        return Result.success(likeService.addUserLike(authentication.getPrincipal().toString(),addUserLikeRequest));
    }
    @PostMapping("/getLike")
    public Result<GetLikeInfoResponse> getUserLike(Authentication authentication, @RequestBody GetUserLikeRequest getUserLikeRequest){
        return Result.success(likeService.getUserLike(authentication.getPrincipal().toString(), getUserLikeRequest));
    }
}
