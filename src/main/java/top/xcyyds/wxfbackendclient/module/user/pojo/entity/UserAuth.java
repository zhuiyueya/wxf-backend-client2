package top.xcyyds.wxfbackendclient.module.user.pojo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */
@Data
@Entity
@Table(name="user_auth")
public class UserAuth {
    @Id
    private long id;
    /**
     * 关联的用户ID
     */
    private long userInternalId;
    /**
     * 登录方式类型
     */
    private LoginType authType;
    /**
     * 唯一标识（如openid/加密手机号）
     */
    private String authKey;
}
