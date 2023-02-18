package org.han.examination.intercepter;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.han.examination.enumerate.ErrorEnum;
import org.han.examination.exception.BusinessException;
import org.han.examination.utils.JedisUtil;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private JedisUtil jedisUtil;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {

        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;
        }

        String username = Optional.ofNullable(request.getHeader("username")).orElseThrow(() -> new BusinessException(ErrorEnum.NO_LOGIN));
        String token = Optional.ofNullable(request.getHeader("token")).orElseThrow(() -> new BusinessException(ErrorEnum.NO_LOGIN));
        try (Jedis jedis = jedisUtil.getJedis()) {
            String jedisToken = Optional.ofNullable(jedis.get("exam:login:" + username)).orElseThrow(() -> new BusinessException(ErrorEnum.NO_LOGIN));
            if (!token.equals(jedisToken)) {
                throw new BusinessException(ErrorEnum.NO_LOGIN);
            }
            jedis.setex("exam:login:" + username, 600, token);
        }
        return true;
    }
}
