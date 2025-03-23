package top.xcyyds.wxfbackendclient.module.user.pojo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-23
 * @Description:
 * @Version:
 */
@Data
@Entity
@Table
public class Clazz {
    /**
     *班级唯一id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clazzId;
    /**
     *班级名称
     */
    private String name;
    /**
     *关联的专业
     */
    @ManyToOne
    @JoinColumn(name="major_id")
    @JsonManagedReference//主端，正常序列化，找到绑定的另一张表的数据
    private Major major;
    /**
     *关联的学生学籍信息
     */
    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference//标记为从端，不进行序列化，即搜索时不再对应的另一张表
    private List<UserSchoolEnrollInfo> userSchoolEnrollInfos;
}
