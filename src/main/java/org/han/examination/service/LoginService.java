package org.han.examination.service;

import jakarta.annotation.Resource;
import org.han.examination.exception.BusinessException;
import org.han.examination.mapper.UserMapper;
import org.han.examination.pojo.data.UsersDO;
import org.han.examination.pojo.dto.LoginInfoDTO;
import org.han.examination.result.Result;
import org.han.examination.utils.JedisUtil;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Optional;
import java.util.UUID;

@Service
public class LoginService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private JedisUtil jedisUtil;
    public Result<String> login(LoginInfoDTO loginInfo) {
        UsersDO usersDO = Optional.ofNullable(userMapper.getUserByUsername(loginInfo.getUsername())).orElseThrow(() -> new BusinessException("用户名或密码错误"));
        if (!loginInfo.getUserPwd().equals(usersDO.getUserPwd())) {
            throw new BusinessException("用户名或密码错误");
        }

        if (!loginInfo.getRoleId().equals(usersDO.getRoleId())) {
            throw new BusinessException("用户名或密码错误");
        }

        String token = UUID.randomUUID().toString().replace("-", "");

        try (Jedis jedis = jedisUtil.getJedis()){
            jedis.setex("exam:login:" + loginInfo.getUsername(), 600, token);
        }

        return Result.success(token);
    }

    public Result<Void> logout(String username) {
        try (Jedis jedis = jedisUtil.getJedis()){
            jedis.del("exam:login:" + username);
        }
        return Result.success();
    }
}
