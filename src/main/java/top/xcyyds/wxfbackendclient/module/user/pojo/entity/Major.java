package top.xcyyds.wxfbackendclient.module.user.pojo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */
@Data
@Entity
@Table
public class Major {

    /**
     * 专业唯一ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long majorId;
    /**
     * 专业名称
     */
    private String name;

    /**
     *绑定院系
     */
    @ManyToOne
    @JoinColumn(name = "department_id")  // 显式指定外键列名
    @JsonManagedReference//主端，正常序列化，找到绑定的另一张表的数据
    private Department department;  // 关联的 Department 实体
    /**
     *绑定的班级
     */
    @OneToMany(mappedBy = "major")
    @JsonBackReference//标记为从端，不进行序列化，即搜索时不再对应的另一张表
    private List<Clazz> clazzes;
}
