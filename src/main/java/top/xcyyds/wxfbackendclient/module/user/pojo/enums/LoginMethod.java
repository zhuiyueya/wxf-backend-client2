package top.xcyyds.wxfbackendclient.module.user.pojo.enums;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */


public enum LoginMethod {
    QQ(0,"qq"),
    WECHAT(1,"wx"),
    STUDENT_ID(2,"sid"),
    PHONE(3,"phone");
    private final int code;
    private final String value;

    LoginMethod(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
