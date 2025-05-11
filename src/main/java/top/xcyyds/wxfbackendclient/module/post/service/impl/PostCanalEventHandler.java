package top.xcyyds.wxfbackendclient.module.post.service.impl;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.lontten.canal.service.CanalEventHandler;
import lombok.extern.slf4j.Slf4j;
import top.xcyyds.wxfbackendclient.module.post.pojo.entity.Post;
import top.xcyyds.wxfbackendclient.util.CanalConverter;

import java.util.List;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-11
 * @Description:
 * @Version:
 */

@Slf4j
public class PostCanalEventHandler implements CanalEventHandler {
    @Override
    public String tableName() {
        return "post";
    }

    @Override
    public void sync(CanalEntry.EventType eventType, Long id, CanalEntry.RowData rowData) {
        CanalEventHandler.super.sync(eventType, id, rowData);
    }

    @Override
    public void insert(Long id, List<CanalEntry.Column> list) {
        Post post= CanalConverter.convert(list, Post.class);
        log.debug("insert post: {}", post);
        CanalEventHandler.super.insert(id, list);
    }

    @Override
    public void update(Long id, List<CanalEntry.Column> oldList, List<CanalEntry.Column> newList) {
        CanalEventHandler.super.update(id, oldList, newList);
    }

    @Override
    public void delete(Long id, List<CanalEntry.Column> list) {
        CanalEventHandler.super.delete(id, list);
    }
}
