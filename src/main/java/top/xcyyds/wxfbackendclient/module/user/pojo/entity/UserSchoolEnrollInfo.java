package top.xcyyds.wxfbackendclient.module.user.pojo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */
@lombok.Data
@Entity
@Table(name="user_school_enroll_info")
public class UserSchoolEnrollInfo {
    /**
     *主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long schoolEnrollInfoId;
    /**
     * 学号（AES-256加密存储）
     */
    private String studentid;
    /**
     * 关联的用户
     */
    @OneToOne
    @JoinColumn(name = "internal_id")
    @JsonBackReference//标记为从端，不进行序列化，即搜索时不再对应的另一张表
    private User user;

    /**
     *关联的班级
     */
    @ManyToOne
    @JsonManagedReference//主端，正常序列化，找到绑定的另一张表的数据
    @JoinColumn(name = "clazz_id")  // 显式指定外键列名
    private Clazz clazz;
}
