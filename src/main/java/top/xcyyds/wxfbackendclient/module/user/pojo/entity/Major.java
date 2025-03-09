package top.xcyyds.wxfbackendclient.module.user.pojo.entity;

import lombok.Data;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */
@Data
public class Major {
    /**
     * 关联的院系ID
     */
    private long departmentid;
    /**
     * 专业唯一ID
     */
    private long id;
    /**
     * 专业名称
     */
    private String name;
}
