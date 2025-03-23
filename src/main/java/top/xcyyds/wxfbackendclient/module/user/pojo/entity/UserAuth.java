package top.xcyyds.wxfbackendclient.module.user.pojo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
    /**
     *唯一ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long authId;

    /**
     * 登录方式类型
     */
    private LoginType authType;
    /**
     * 唯一标识（如openid/加密手机号）
     */
    private String authKey;

    @ManyToOne
    @JoinColumn(name="internal_id")
    @JsonBackReference//标记为从端，不进行序列化，即搜索时不再对应的另一张表
    private User user;
}
