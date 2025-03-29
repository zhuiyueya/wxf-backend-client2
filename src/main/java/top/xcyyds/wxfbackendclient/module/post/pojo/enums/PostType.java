package top.xcyyds.wxfbackendclient.module.post.pojo.enums;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-29
 * @Description:
 * @Version:
 */

public enum PostType {
    HELP_POST(0,"HELP_POST"),
    SECOND_SALED_POST(1,"SECOND_SALED_POST"),
    LOST_POST(2,"LOST_POST"),
    ANY_POST(3,"ANY_POST");
    private final int code;
    private final String value;

    PostType(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
