package com.baizhi.interceptors;

import com.baizhi.annotations.RequiredToken;
import com.baizhi.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);

    private RedisTemplate redisTemplate;

    @Autowired
    public TokenInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取当前请求方法上是否存在RequiredToken注解
        boolean requiredToken = ((HandlerMethod) handler).getMethod().isAnnotationPresent(RequiredToken.class);
        //2.存在该注解
        if (requiredToken) {
            //1.获取token信息
            String token = request.getParameter("token");
            log.info("当前传递的token为: {}", token);
            //2.拼接前缀
            String tokenKey = "session_" + token;
            //3.根据tokenKey获取用户信息
            User o = (User) redisTemplate.opsForValue().get(tokenKey);
            if (o == null) throw new RuntimeException("提示: 令牌无效,无效token!");
            //4.存储到当前请求的上下文中
            request.setAttribute("token", token);
            request.setAttribute("user", o);
        }
        return true;
    }
}
