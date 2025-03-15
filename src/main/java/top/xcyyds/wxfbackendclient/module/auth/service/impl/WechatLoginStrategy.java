package top.xcyyds.wxfbackendclient.module.auth.service.impl;

import ch.qos.logback.core.joran.spi.HttpUtil;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
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

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-13
 * @Description:
 * @Version:
 */

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

        UserAuth userAuth=userAuthRepository.findByAuthKey(openId);
        //触发注册逻辑
        if(userAuth==null){
            user=createUserWithOpenId(openId,wechatLoginRequest.getLoginType());
        }else{
            user=userRepository.findByInternalId(userAuth.getUserInternalId());
        }

        return user;
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

        //指定为东八区时间（偏移量“+08：00"）
        OffsetDateTime beijingTime = OffsetDateTime.now(ZoneOffset.ofHours(8));
        Long internalId=idGenerator.generateInternalId();

        user.setInternalId(internalId);
        user.setPublicId(idGenerator.generatePublicId(internalId));
        user.setCreatedAt(beijingTime);
        user.setUpdatedAt(beijingTime);
        user=userRepository.save(user);

        userAuth.setUserInternalId(user.getInternalId());
        userAuth.setAuthType(loginType);
        userAuth.setAuthKey(openId);
        userAuthRepository.save(userAuth);

        return user;
    }

    @Override
    public boolean canSupportedLoginType(LoginType loginType) {
        return loginType.equals(LoginType.WECHAT_LOGIN);
    }
}
