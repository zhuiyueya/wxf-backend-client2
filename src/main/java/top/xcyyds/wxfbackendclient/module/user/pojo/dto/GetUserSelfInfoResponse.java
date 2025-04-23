package top.xcyyds.wxfbackendclient.module.user.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-22
 * @Description:
 * @Version:
 */
@lombok.Data
public class GetUserSelfInfoResponse {


    /**
     * 图片相对路径，图片相对路径（即不包含ip和端口，前端获取路径后用来请求图片服务器
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
     * 武纺币
     */
    private long money;
    /**
     * 昵称，用户昵称
     */
    private String nickName;
    /**
     * 帖子数量
     */
    private long postCount;
    /**
     * 用户对外唯一标识符
     */
    private String publicId;
    /**
     * 角色权限
     */
    private long role;

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
