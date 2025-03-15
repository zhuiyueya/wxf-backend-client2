package top.xcyyds.wxfbackendclient.config;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-14
 * @Description:
 * @Version:
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity

public class SecurityConfig {



    /*
     * @Description:
     * @param null
     * @return:
     * @Author:  34362
     * @date:  2025/2/20 11:01
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())//关闭csrf
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/login").permitAll()//对外公开的接口
                        .anyRequest().authenticated()//其他接口需要验证
                );


        return http.build();
    }

}
