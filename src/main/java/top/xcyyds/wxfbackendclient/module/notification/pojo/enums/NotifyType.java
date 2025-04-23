package top.xcyyds.wxfbackendclient.module.notification.pojo.enums;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-11
 * @Description:
 * @Version:
 */

public enum NotifyType {
    ANNOUNCE(1,"ANNOUNCE"),        // 通知，如系统的通告等
    REMINDER(2,"REMINDER");           // 提醒，例如被点赞，被回复等

    private final int code;
    private final String value;

    NotifyType(int code, String value) {
        this.code = code;
        this.value = value;
    }
    public int getCode() {
        return code;
    }
}
