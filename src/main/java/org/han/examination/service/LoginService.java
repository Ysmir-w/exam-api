package org.han.examination.service;

import jakarta.annotation.Resource;
import org.han.examination.exception.BusinessException;
import org.han.examination.mapper.UserinfoMapper;
import org.han.examination.pojo.data.UserinfoDO;
import org.han.examination.pojo.dto.LoginInfoDTO;
import org.han.examination.pojo.vo.LoginResultVO;
import org.han.examination.result.Result;
import org.han.examination.utils.JedisUtil;
import org.han.examination.utils.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Optional;
import java.util.UUID;

@Service
public class LoginService {

    @Resource
    private UserinfoMapper userinfoMapper;

    @Resource
    private JedisUtil jedisUtil;
    @Resource
    private JsonUtil jsonUtil;

    public Result<LoginResultVO> login(LoginInfoDTO loginInfo) {
        UserinfoDO userinfo = Optional.ofNullable(userinfoMapper.getUserInfoByPhoneAndPassword(loginInfo)).orElseThrow(() -> new BusinessException("用户名或密码错误"));

        String token = UUID.randomUUID().toString();
        LoginResultVO loginResultVO = new LoginResultVO();
        BeanUtils.copyProperties(userinfo, loginResultVO);
        loginResultVO.setToken(token);

        try (Jedis jedis = jedisUtil.getJedis()) {
            String key = "exam:login:" + loginInfo.getPhone();
            long ttl = 60 * 10;
            jedis.setex(key, ttl, jsonUtil.serialization(loginResultVO));
        }
        return Result.success(loginResultVO);
    }
}
