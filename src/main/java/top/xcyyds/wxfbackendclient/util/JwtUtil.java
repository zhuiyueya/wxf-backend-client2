package top.xcyyds.wxfbackendclient.util;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-15
 * @Description:jwt组件，用来生成和验证token
 * @Version:v1
 */
@Slf4j
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private  String secret;// 用于存储 JWT 签名密钥的私有字符串字段。
    @Value("${jwt.expiration}")
    private  long expiration;// 存储 JWT 过期时间的私有长整型字段。

    // 使用给定的密钥字节创建 HMAC SHA-256 密钥。
    public  SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /*
     * @Description: 生成token
     * @param subject：要融合到token里的信息
     * @return: String
     * @Author:  chasemoon
     * @date:  2025/3/15 20:13
     */
    public  String generateToken(String subject) {
        Date now=new Date();
        Date expireDate=new Date(now.getTime()+expiration);// 计算出令牌的到期时间。

        return Jwts.builder()// 开始构建 JWT 对象
                .setSubject(subject)// 设置 JWT 的主体信息
                .setIssuedAt(now)// 设置 JWT 的签发时间
                .setExpiration(expireDate)// 设置 JWT 的过期时间
                .signWith(getSigningKey())// 使用之前定义的签名密钥对 JWT 进行签名。
                .compact();// 将整个 JWT 编码为紧凑形式的字符串并返回。
    }

    public  String validateToken(String token) {
        try{

            Claims claims=Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            //返回public_id
            return claims.getSubject();
        }catch (ExpiredJwtException | UnsupportedJwtException
                | MalformedJwtException | SignatureException | IllegalArgumentException e){
            throw new RuntimeException("Invalid token"+e.getMessage());
        }
    }

}

