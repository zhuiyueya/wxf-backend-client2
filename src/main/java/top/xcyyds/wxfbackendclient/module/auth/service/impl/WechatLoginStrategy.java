package top.xcyyds.wxfbackendclient.module.auth.service.impl;

import ch.qos.logback.core.joran.spi.HttpUtil;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.WechatLoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.service.AbstractLoginStrategy;
import top.xcyyds.wxfbackendclient.module.user.persistence.repository.UserAuthRepository;
import top.xcyyds.wxfbackendclient.module.user.persistence.repository.UserRepository;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.User;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.UserAuth;
import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;
import top.xcyyds.wxfbackendclient.util.IdGenerator;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-13
 * @Description:
 * @Version:
 */
@Slf4j
@Component
public class WechatLoginStrategy extends AbstractLoginStrategy {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private IdGenerator idGenerator;

    @Override
    public AuthenticationResult authenticate(LoginRequest loginRequest) {
        WechatLoginRequest wechatLoginRequest=(WechatLoginRequest)loginRequest;
        String openId=getWechatOpenId(wechatLoginRequest.getCode());

        //to do 从redis中查找appId对应的InternalId，加速查找

        User user;
        AuthenticationResult authenticationResult=new AuthenticationResult();

        UserAuth userAuth=userAuthRepository.findByAuthKey(openId);

        //触发注册逻辑
        if(userAuth==null){
            user=createUserWithOpenId(openId,wechatLoginRequest.getLoginType());
            authenticationResult.setIsNewUser(true);
        }else{//登录逻辑
            //user=userRepository.findByInternalId(userAuth.getUser().getInternalId());
            user=userAuth.getUser();
            authenticationResult.setIsNewUser(false);
        }

        authenticationResult.setUser(user);
        return authenticationResult;
    }

    private String getWechatOpenId(String code){
        try{
            WxMaJscode2SessionResult session=wxMaService.jsCode2SessionInfo(code);
            return session.getOpenid();
        }catch(WxErrorException e){
            throw new RuntimeException("微信登录失败:"+e.getMessage());
        }

    }

    private User createUserWithOpenId(String openId,LoginType loginType){
        User user=new User();
        UserAuth userAuth=new UserAuth();
        log.info("创建用户开始");
        //指定为东八区时间（偏移量“+08：00"）
        OffsetDateTime beijingTime = OffsetDateTime.now(ZoneOffset.ofHours(8));
        Long internalId=idGenerator.generateInternalId();

        user.setInternalId(internalId);
        user.setPublicId(idGenerator.generatePublicId(internalId));
        user.setCreatedAt(beijingTime);
        user.setUpdatedAt(beijingTime);
        user.setLevel(0);
        user.setMoney(0);
        user.setRole(0);

        user.setUserAuths(new ArrayList<>());
        user.getUserAuths().add(userAuth);

        userAuth.setAuthType(loginType);
        userAuth.setAuthKey(openId);
        userAuth.setUser(user);

        user=userRepository.save(user);

        return user;
    }

    @Override
    public boolean canSupportedLoginType(LoginType loginType) {
        return loginType.equals(LoginType.WECHAT_LOGIN);
    }
}
