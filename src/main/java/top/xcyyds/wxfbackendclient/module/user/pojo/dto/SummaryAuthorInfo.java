package top.xcyyds.wxfbackendclient.module.user.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */

/**
 * ApifoxModel
 */
@lombok.Data
public class SummaryAuthorInfo {
    /**
     * 作者头像URL（CDN加速）
     */
    private String avatar;
    /**
     * 作者昵称（可显示）
     */
    private String nickName;
    /**
     * 作者对外标识
     */
    private String publicId;
}

