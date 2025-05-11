package top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import top.xcyyds.wxfbackendclient.common.ContentState;
import top.xcyyds.wxfbackendclient.module.comment.pojo.entity.Comment;
import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;
import top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.enums.MediaType;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;

import java.time.OffsetDateTime;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-10
 * @Description: 定义媒体附件在 Elasticsearch 索引中作为帖子文档 (PostEsDocument) 嵌套对象的结构。
 * @Version: v1
 */
@Data
public class MediaAttachmentEsDocument {
    @Field(type = FieldType.Long) // 或者 Keyword，如果attachId主要用于精确匹配而非范围查询
    private Long attachId;

    @Field(type = FieldType.Keyword) // 枚举类型通常映射为 Keyword
    private MediaType mediaType; // 假设 MediaType 是一个枚举

    @Field(type = FieldType.Keyword) // URL 或路径，通常用 Keyword 进行精确匹配
    private String storagePath;

    @Field(type = FieldType.Long)
    private Long fileSize;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private OffsetDateTime uploadTime;

    @Field(type = FieldType.Keyword) // 枚举类型通常映射为 Keyword
    private ContentState status; // 假设 ContentState 是一个枚举
}
