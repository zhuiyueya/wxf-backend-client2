package top.xcyyds.wxfbackendclient.util;

import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.xcyyds.wxfbackendclient.constant.JwtConstants;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-23
 * @Description:
 * @Version:
 */


@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=request.getHeader(JwtConstants.REQUEST_HEADER_AUTHORIZATION);
        log.info("token:"+token);

        if(token!=null && token.startsWith(JwtConstants.TOKEN_PREFIX_BEARER)) {
            log.info("token bearer existed!");
            //去掉“Bearer "前缀
            token=token.substring(JwtConstants.TOKEN_PREFIX_BEARER_LENGTH);
            log.info("token:"+token);
            try{
                String publicId= jwtUtil.validateToken(token);
                log.info("publicId:"+publicId);
                UsernamePasswordAuthenticationToken auth =new UsernamePasswordAuthenticationToken(publicId,null,new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(auth);

            }catch(RuntimeException e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,JwtConstants.INVALID_TOKEN);
                response.flushBuffer();   // 确保响应立即发送
                return;
            }

        }
        filterChain.doFilter(request, response);
    }
}
