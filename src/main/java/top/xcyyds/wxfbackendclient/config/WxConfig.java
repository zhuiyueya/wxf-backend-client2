package top.xcyyds.wxfbackendclient.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-14
 * @Description:
 * @Version:
 */

@Configuration
public class WxConfig {
    @Value("${wx.ma.configs[0].appid}")
    private String appId;
    @Value("${wx.ma.configs[0].secret}")
    private String appSecret;
    @Bean
    public WxMaService wxMaService(){
        WxMaDefaultConfigImpl config=new WxMaDefaultConfigImpl();
        config.setAppid(appId);
        config.setSecret(appSecret);
        WxMaService service=new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }
}
