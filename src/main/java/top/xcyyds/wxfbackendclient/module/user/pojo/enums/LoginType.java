package top.xcyyds.wxfbackendclient.module.user.pojo.enums;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:
 * @Version:
 */


public enum LoginType {
    QQ_LOGIN(0,"qq"),
    WECHAT_LOGIN(1,"wx"),
    STUDENT_ID_LOGIN(2,"sid"),
    PHONE_LOGIN(3,"phone");
    private final int code;
    private final String value;

    LoginType(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
