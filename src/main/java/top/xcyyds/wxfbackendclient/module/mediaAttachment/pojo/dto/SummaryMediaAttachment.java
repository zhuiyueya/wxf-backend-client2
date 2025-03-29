package top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.dto;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */


/**
 * summaryMediaAttachment
 */
@lombok.Data
public class SummaryMediaAttachment {
    /**
     * 文件大小
     */
    private long fileSize;
    /**
     * 媒体类型
     */
    private String mediaType;
    /**
     * CDN加速的媒体路径（带访问签名）
     */
    private String storagePath;
}

