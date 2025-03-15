package top.xcyyds.wxfbackendclient.util;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-15
 * @Description:时间递增雪花算法生成ID
 * @Version:
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SnowflakeIdGenerator {
    // 时间起始标记点（可调整为项目启动时间）
    private final static long EPOCH = 1710000000000L; // 2024-03-10 00:00:00

    // 各部分位数
    private final static long WORKER_ID_BITS = 5L;    // 机器ID位数
    private final static long DATACENTER_ID_BITS = 5L; // 数据中心ID位数
    private final static long SEQUENCE_BITS = 12L;     // 序列号位数

    // 最大值
    private final static long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private final static long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    // 位移
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private final static long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private final static long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    private final static long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    @Value("${app.id-generator.snowflake.worker-id:1}")
    private long workerId;

    @Value("${app.id-generator.snowflake.datacenter-id:1}")
    private long datacenterId;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    @PostConstruct
    public void init() {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("Worker ID 必须介于 0 和 " + MAX_WORKER_ID + " 之间");
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID 必须介于 0 和 " + MAX_DATACENTER_ID + " 之间");
        }
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        // 处理时钟回拨
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("时钟回拨，拒绝生成ID。回拨时间：" + (lastTimestamp - timestamp) + "ms");
        }

        // 同一毫秒内序列递增
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 当前毫秒序列号用完，等待下一毫秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
