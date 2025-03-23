package top.xcyyds.wxfbackendclient.constant;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-23
 * @Description:
 * @Version:
 */

public class JwtConstants {
    // 定义HTTP头部字段名常量
    public static final String REQUEST_HEADER_AUTHORIZATION = "Authorization";

    // 定义Bearer Token前缀常量
    public static final String TOKEN_PREFIX_BEARER = "Bearer ";

    // 定义Bearer Token前缀的长度常量
    public static final int TOKEN_PREFIX_BEARER_LENGTH = TOKEN_PREFIX_BEARER.length();

    //无效Token常量
    public static final String INVALID_TOKEN = "Invalid Token";
}
