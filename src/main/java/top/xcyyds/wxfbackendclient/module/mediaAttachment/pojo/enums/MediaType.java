package top.xcyyds.wxfbackendclient.module.mediaAttachment.pojo.enums;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-07
 * @Description:
 * @Version:
 */

public enum MediaType {

    IMAGE_PNG(0,"IMAGE_PNG"),
    IMAGE_JPG(1,"IMAGE_JPG"),
    IMAGE_JPEG(2,"IMAGE_JPEG"),
    VIDEO_MP4(3,"VIDEO_MP4"),
    VIDEO_WAV(4,"VIDEO_WAV");


    private final int code;
    private final String value;

    MediaType(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
