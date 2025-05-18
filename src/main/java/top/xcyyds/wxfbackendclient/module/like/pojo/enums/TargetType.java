package top.xcyyds.wxfbackendclient.module.like.pojo.enums;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */


import java.io.IOException;

/**
 * 附件所属的内容类型
 */
public enum TargetType {
    COMMENT(0,"COMMENT"),
    POST(1,"POST");

    private final int code;
    private final String value;

    TargetType(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public String toValue() {
        switch (this) {
            case COMMENT: return "COMMENT";
            case POST: return "POST";
        }
        return null;
    }

    public static TargetType forValue(String value) throws IOException {
        if (value.equals("COMMENT")) return COMMENT;
        if (value.equals("POST")) return POST;
        throw new IOException("Cannot deserialize TargetType");
    }
}

