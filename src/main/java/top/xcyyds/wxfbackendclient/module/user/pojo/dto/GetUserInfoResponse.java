package top.xcyyds.wxfbackendclient.module.user.pojo.dto;

import lombok.Data;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-03
 * @Description:
 * @Version:
 */
@Data
public class GetUserInfoResponse {
    /**
     * 图片完整路径，图片完整路径（即包含ip和端口，前端获取路径后用来请求图片服务器
     */
    private String avatar;
    /**
     * 学院名称
     */
    private String departmentName;
    /**
     * 用户等级
     */
    private long level;
    /**
     * 专业名称
     */
    private String majorName;
    /**
     * 昵称，用户昵称
     */
    private String nickName;
    /**
     * 用户对外唯一标识符
     */
    private String publicId;
    /**
     * 角色权限
     */
    private long role;
}
