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
public class Department {
    /**
     * 院系唯一ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long departmentId;
    /**
     * 院系名称
     */
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference//标记为从端，不进行序列化，即搜索时不再对应的另一张表
    private List<Major>majors;
}
