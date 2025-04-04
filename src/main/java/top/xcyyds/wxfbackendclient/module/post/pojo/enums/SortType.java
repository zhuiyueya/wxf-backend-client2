package top.xcyyds.wxfbackendclient.module.post.pojo.enums;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-04-04
 * @Description:
 * @Version:
 */

public enum SortType {
    TIME_DESCENDING(0,"TIME_DESCENDING_SORTED"),//时间倒叙，从新到旧
    TIME_ASCENDING(1,"TIME_ASCENDING"),//时间正序，从旧到新
    HOT(2,"HOT");
    private final int code;
    private final String value;

    SortType(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
