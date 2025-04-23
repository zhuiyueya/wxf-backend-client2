package top.xcyyds.wxfbackendclient.module.user.pojo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import top.xcyyds.wxfbackendclient.module.like.pojo.entity.Like;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */
@Table(name="user")
@lombok.Data
@Entity
public class User {
    /**
     * 头像URL
     */
    private String avatar;
    /**
     * 创建时间
     */
    private OffsetDateTime createdAt;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 访客ID（设备唯一）
     */
    private String guestId;
    /**
     * 用户唯一对内ID（雪花算法）
     */
    @Id
    private long internalId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 用户唯一对外ID（HMAC-SHA256加密）
     */
    private String publicId;
    /**
     * 姓名（加密存储）
     */
    private String realName;
    /**
     * 角色权限
     */
    private long role;
    /**
     * 更新时间
     */
    private OffsetDateTime updatedAt;
    /**
     * 用户等级
     */
    private long level;
    /**
     * 武纺币
     */
    private long money;
    /**
     * 帖子数量
     */
    private long postCount;
    /**
     *关联学籍表
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference//主端，正常序列化，找到绑定的另一张表的数据
    private UserSchoolEnrollInfo enrollInfo;  // 一对一关联
    /**
     *关联的用户认证信息
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference//主端，正常序列化，找到绑定的另一张表的数据
    private List<UserAuth> userAuths;

    // 新增：用户关联的点赞记录
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Like> likes;
}
