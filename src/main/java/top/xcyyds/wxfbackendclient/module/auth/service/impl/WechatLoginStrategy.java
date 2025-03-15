package top.xcyyds.wxfbackendclient.module.auth.service.impl;

import ch.qos.logback.core.joran.spi.HttpUtil;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.service.AbstractLoginStrategy;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.User;
import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-13
 * @Description:
 * @Version:
 */

@Component
public class WechatLoginStrategy extends AbstractLoginStrategy {

    @Autowired
    private WxMaService wxMaService;
    @Override
    public User authenticate(LoginRequest loginRequest) {
        return null;
    }

    private String getWechatOpenId(String code){
        try{
            WxMaJscode2SessionResult session=wxMaService.jsCode2SessionInfo(code);
            return session.getOpenid();
        }catch(WxErrorException e){
            throw new RuntimeException("微信登录失败:"+e.getMessage());
        }

    }

    @Override
    public boolean canSupportedLoginType(LoginType loginType) {
        return loginType.equals(LoginType.WECHAT_LOGIN);
    }
}
