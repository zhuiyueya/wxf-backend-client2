package top.xcyyds.wxfbackendclient.module.like.pojo.dto;

import top.xcyyds.wxfbackendclient.common.ContentState;

/**
 * @Author: theManager
 * @CreateTime: 2025-04-09
 * @Description:用户点赞的响应数据流对象
 * @Version:
 */

@lombok.Data
public class GetLikeInfoResponse {
    /*
     * 点赞状态
     */
    private ContentState status;
    /*
     * 点赞数量
     */
    private long likeCount;
    /*
     * 点赞实体id
     */
    private long likeId;

}
