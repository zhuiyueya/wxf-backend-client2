package top.xcyyds.wxfbackendclient.module.user.pojo.entity;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */
@lombok.Data
public class UserSchoolEnrollInfo {
    /**
     * 班级名称
     */
    private String apifoxSchemaClass;
    /**
     * 关联的专业ID
     */
    private long majorid;
    /**
     * 学号（AES-256加密存储）
     */
    private String studentid;
    /**
     * 关联的用户ID
     */
    private long userInternalid;
}
