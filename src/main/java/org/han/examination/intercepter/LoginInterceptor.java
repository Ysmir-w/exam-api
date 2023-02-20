package org.han.examination.intercepter;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.han.examination.enumerate.ErrorEnum;
import org.han.examination.exception.BusinessException;
import org.han.examination.pojo.vo.LoginVO;
import org.han.examination.utils.JedisUtil;
import org.han.examination.utils.JsonUtil;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private JedisUtil jedisUtil;
    @Resource
    private JsonUtil jsonUtil;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {

        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;
        }

        String username = Optional.ofNullable(request.getHeader("username")).orElseThrow(() -> new BusinessException(ErrorEnum.NO_LOGIN));
        String token = Optional.ofNullable(request.getHeader("token")).orElseThrow(() -> new BusinessException(ErrorEnum.NO_LOGIN));
        try (Jedis jedis = jedisUtil.getJedis()) {
            String info = Optional.ofNullable(jedis.get("exam:login:" + username)).orElseThrow(() -> new BusinessException(ErrorEnum.NO_LOGIN));
            LoginVO loggedInfo = jsonUtil.deserialize(info, LoginVO.class);
            if (!token.equals(loggedInfo.getToken())) {
                throw new BusinessException(ErrorEnum.NO_LOGIN);
            }
            jedis.expire("exam:login:" + username, 600);
        }
        return true;
    }
}
