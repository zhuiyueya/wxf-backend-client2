package top.xcyyds.wxfbackendclient.util;

import com.alibaba.fastjson2.TypeReference;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-05-11
 * @Description: 用于将 Canal 监听到的 MySQL Binlog 事件（即 `List<CanalEntry.Column>` 列数据）**自动转换为 Java 对象**。
 * @Version: v1
 */
public class CanalConverter {
    private static final ObjectMapper mapper = new ObjectMapper();

    // 针对简单类型
    public static <T> T convert(List<CanalEntry.Column> columns, Class<T> clazz) {
        return convert(columns, mapper.getTypeFactory().constructType(clazz));
    }

    // 针对泛型类型
    public static <T> T convert(List<CanalEntry.Column> columns, TypeReference<T> typeRef) {
        JavaType javaType = mapper.getTypeFactory().constructType(typeRef.getType());
        return convert(columns, javaType);
    }

    private static <T> T convert(List<CanalEntry.Column> columns, JavaType javaType) {
        try {
            Map<String, Object> fieldMap = columns.stream()
                    .collect(Collectors.toMap(
                            CanalEntry.Column::getName,
                            c -> c.getValue() == null ? null : c.getValue()
                    ));
            return mapper.readValue(mapper.writeValueAsString(fieldMap), javaType);
        } catch (Exception e) {
            throw new RuntimeException("Canal 转换失败", e);
        }
    }
}
