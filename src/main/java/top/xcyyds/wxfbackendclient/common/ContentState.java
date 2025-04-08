package top.xcyyds.wxfbackendclient.common;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-07
 * @Description:
 * @Version:
 */
public enum ContentState {
    // 基础状态
    DRAFT(0,"DRAFT"),           // 草稿
    PUBLISHED(1,"PUBLISHED"),       // 已发布
    DELETED(2,"DELETED"),         // 已删除

    // 审核流程
    UNDER_REVIEW(3,"UNDER_REVIEW"),    // 审核中
    APPROVED(4,"APPROVED"),        // 审核通过
    REJECTED(5,"REJECTED"),        // 审核驳回

    // 运营状态
    ARCHIVED(6,"ARCHIVED"),        // 已归档
    PINNED(7,"PINNED");           // 置顶

    private final int code;
    private final String value;

    ContentState(int code, String value) {
        this.code = code;
        this.value = value;
    }
    public int getCode() {
        return code;
    }
}
