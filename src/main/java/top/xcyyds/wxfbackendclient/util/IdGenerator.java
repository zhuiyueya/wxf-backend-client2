package top.xcyyds.wxfbackendclient.util;
// 必须添加以下导包语句

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;
/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-14
 * @Description:
 * @Version:
 */


@Component
public class IdGenerator {
    private final String hmacSecret;
    private final SnowflakeIdGenerator snowflake;

    // 通过构造器注入配置和雪花算法实例
    public IdGenerator(
            @Value("${app.id-generator.hmac-secret}") String hmacSecret,
            SnowflakeIdGenerator snowflake) {
        this.hmacSecret = hmacSecret;
        this.snowflake = snowflake;
    }

    public long generateInternalId() {
        return snowflake.nextId();
    }

    public String generatePublicId(long internalId) {
        try {
            String salt = generateSalt();
            long timestamp = System.currentTimeMillis();
            String rawData = String.format("%d-%d-%s", internalId, timestamp, salt);

            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec key = new SecretKeySpec(hmacSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmac.init(key);
            byte[] hash = hmac.doFinal(rawData.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("HMAC 生成失败", e);
        }
    }

    private String generateSalt() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
