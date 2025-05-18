package top.xcyyds.wxfbackendclient.module.user.pojo.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-18
 * @Description:
 * @Version:
 */
@Data
@Document(indexName = "summary_author_info")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummaryAuthorInfoEsDocument {
    /**
     * 作者头像URL（CDN加速）
     */
    @Field(type = FieldType.Keyword)
    private String avatar;
    /**
     * 作者昵称（可显示）
     */
    @Field(type = FieldType.Keyword)
    private String nickName;
    /**
     * 作者对外标识
     */
    @Id
    private String publicId;
}
