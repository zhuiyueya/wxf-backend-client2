package top.xcyyds.wxfbackendclient.module.user.pojo.dto;

import lombok.Data;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-24
 * @Description:
 * @Version:
 */

@Data
public class UpdateUserSelfMajorRequest {
    private String publicId;
    private int majorId;
}
