package top.xcyyds.wxfbackendclient.module.auth.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */


@lombok.Data
public class WechatLoginRequest {
    /**
     * wx.login获取的临时code
     */
    private String code;
    /**
     * 可选，用户授权后通过wx.getUserProfile获取的加密数据
     */
    private String encryptedData;
    /**
     * 可选，加密算法的初始向量
     */
    private String iv;
}

