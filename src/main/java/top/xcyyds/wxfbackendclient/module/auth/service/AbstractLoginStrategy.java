package top.xcyyds.wxfbackendclient.module.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginRequest;
import top.xcyyds.wxfbackendclient.module.auth.pojo.dto.LoginResponse;
import top.xcyyds.wxfbackendclient.module.auth.service.impl.AuthService;
import top.xcyyds.wxfbackendclient.module.user.pojo.entity.User;
import top.xcyyds.wxfbackendclient.module.user.pojo.enums.LoginType;
import top.xcyyds.wxfbackendclient.util.JwtUtil;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-13
 * @Description:
 * @Version:
 */

@Component
@Slf4j
public abstract class AbstractLoginStrategy implements LoginStrategy {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        log.info("top/xcyyds/wxfbackendclient/module/auth/service/AbstractLoginStrategy:login start>>>{}",loginRequest);
        User user=authenticate(loginRequest);
        LoginResponse response=new LoginResponse();
        response.setIsNewUser(false);
        if(user==null){
            //register 待定，应该在子strategy类里处理，才能根据不同的登录方式处理
            response.setIsNewUser(true);
        }
        response.setToken(generateToken(user.getPublicId()));

        return response;
    }
    public abstract AuthenticationResult authenticate(LoginRequest loginRequest);

    public String generateToken(String subject) {

        return jwtUtil.generateToken(subject);
    }

    @Override
    public abstract boolean canSupportedLoginType(LoginType loginType) ;

    //用于AbstractLoginStrategy和子类之间交互传输
    protected static class AuthenticationResult {
        private User user;
        private boolean isNewUser;

       public AuthenticationResult(){

       }

        public User getUser() {
            return user;
        }

        public boolean getIsNewUser() {
            return isNewUser;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public void setIsNewUser(boolean newUser) {
            isNewUser = newUser;
        }
    }
}
