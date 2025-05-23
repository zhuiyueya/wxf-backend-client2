package top.xcyyds.wxfbackendclient.module.post.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import top.xcyyds.wxfbackendclient.module.comment.pojo.entity.Comment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity.MediaAttachment;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity.MediaAttachmentEsDocument;
import top.xcyyds.wxfbackendclient.module.post.pojo.enums.PostType;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-10
 * @Description: 定义帖子数据在 Elasticsearch 中的文档模型 (Document Model)，用于搜索引擎的索引和查询。
 * @Version: v1
 */
@Data
@Document(indexName = "post")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostEsDocument {
    @Id // 标记为 Elasticsearch 文档的 ID
    @Field(type = FieldType.Keyword)
    private String postId;

    @Field(type = FieldType.Keyword) // 通常用于精确匹配、聚合和排序的ID
    private String publicId;

    @Field(type = FieldType.Text, analyzer = "standard") // 帖子正文，用于全文搜索，可指定分词器
    private String content;

    //@Field(type = FieldType.Date_Nanos) // 日期时间类型
    @Field(type = FieldType.Date,
            format = {},
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SSSSSS]XXX")
    private OffsetDateTime createTime;

    @Field(type = FieldType.Keyword) // 枚举类型通常映射为 Keyword，用于精确过滤
    private PostType postType; // 假设 PostType 是一个枚举

    @Field(type = FieldType.Boolean)
    private Boolean hasMedia;

    @Field(type = FieldType.Long)
    private Long likeCount;

    @Field(type = FieldType.Long)
    private Long collectCount;

    @Field(type = FieldType.Long)
    private Long replyCount;

    @Field(type = FieldType.Long)
    private Long shareCount;

    @Field(type = FieldType.Long) // 或者是 Keyword 如果您打算将其映射为字符串状态
    private Long status; // 如果这个status会转成 "NORMAL", "DELETED" 等字符串，则用 Keyword

    @Field(type = FieldType.Long)
    private Long totalAttachments; // 如果您决定保留这个字段

    @Field(type = FieldType.Long)
    private Long viewCount;

    @Field(type = FieldType.Nested) // 关键！用于内嵌对象列表
    private List<MediaAttachmentEsDocument> mediaAttachments;
}

