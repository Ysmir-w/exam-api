package org.han.examination.intercepter;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.han.examination.enumerate.ErrorEnum;
import org.han.examination.exception.BusinessException;
import org.han.examination.pojo.data.LoginResultDO;
import org.han.examination.utils.JedisUtil;
import org.han.examination.utils.JsonUtil;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.Jedis;

import java.util.Objects;
import java.util.Optional;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private JedisUtil jedisUtil;
    @Resource
    private JsonUtil jsonUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String token = Optional.ofNullable(request.getHeader("token")).orElse("");
        String phone = Optional.ofNullable(request.getHeader("phone")).orElse("");

        try (Jedis jedis = jedisUtil.getJedis()) {
            String key = "exam:login:" + phone;
            LoginResultDO loginResult = Optional.ofNullable(jedis.get(key))
                    .map(s -> jsonUtil.deserialization(s, LoginResultDO.class))
                    .orElseThrow(() -> new BusinessException(ErrorEnum.NO_LOGIN));
            if (!Objects.equals(token, loginResult.getToken())) {
                throw new BusinessException(ErrorEnum.NO_LOGIN);
            }
            jedis.expire(key, 60 * 10);
        }

        return true;
    }
}
