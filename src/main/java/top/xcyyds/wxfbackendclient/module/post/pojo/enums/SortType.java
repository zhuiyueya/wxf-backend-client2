package top.xcyyds.wxfbackendclient.module.post.pojo.enums;


import top.xcyyds.wxfbackendclient.module.like.pojo.enums.TargetType;

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

    public String getEsFieldNameCreateTime(TargetType targetType){
        switch(this){
            case TIME_DESCENDING:
                switch (targetType){
                    case POST:
                        return "createTime";
                }
            case TIME_ASCENDING:
                switch (targetType){
                    case POST:
                        return "createTime";
                }
            default:
                return "createTime";
        }
    }
    public String getEsFieldNameId(TargetType targetType){
        switch (targetType){
            case POST:
                return "postId";
            default:
                return "id";
        }
    }


    SortType(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
