package org.han.examination.service;

import jakarta.annotation.Resource;
import org.han.examination.exception.BusinessException;
import org.han.examination.mapper.ClassMapper;
import org.han.examination.mapper.UserMapper;
import org.han.examination.pojo.data.ClassDO;
import org.han.examination.pojo.data.UsersDO;
import org.han.examination.pojo.dto.LoginInfoDTO;
import org.han.examination.pojo.dto.PasswordDTO;
import org.han.examination.pojo.vo.LoginVO;
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
    private UserMapper userMapper;

    @Resource
    private ClassMapper classMapper;
    @Resource
    private JedisUtil jedisUtil;

    @Resource
    private JsonUtil jsonUtil;

    public Result<LoginVO> login(LoginInfoDTO loginInfo) {
        UsersDO usersDO = Optional.ofNullable(userMapper.getUserByUsername(loginInfo.getUsername())).orElseThrow(() -> new BusinessException("用户名或密码错误"));
        if (!loginInfo.getUserPwd().equals(usersDO.getUserPwd())) {
            throw new BusinessException("用户名或密码错误");
        }

        if (!loginInfo.getRoleId().equals(usersDO.getRoleId())) {
            throw new BusinessException("用户名或密码错误");
        }

        LoginVO loginVO = new LoginVO();
        String token = UUID.randomUUID().toString().replace("-", "");
        ClassDO classDO = classMapper.getClassById(usersDO.getClassId());
        BeanUtils.copyProperties(usersDO, loginVO);
        loginVO.setToken(token);
        loginVO.setClassName(classDO.getClassName());
        loginVO.setClassId(String.valueOf(classDO.getClassId()));
        loginVO.setRoleId(String.valueOf(usersDO.getRoleId()));
        try (Jedis jedis = jedisUtil.getJedis()) {
            jedis.setex("exam:login:" + loginInfo.getUsername(), 600, jsonUtil.serialize(loginVO));
        }

        return Result.success(loginVO);
    }

    public Result<Void> logout(String username) {
        try (Jedis jedis = jedisUtil.getJedis()) {
            jedis.del("exam:login:" + username);
        }
        return Result.success();
    }

    public Result<Void> updatePassword(String username, PasswordDTO passwordDTO) {
        UsersDO usersDO = Optional.ofNullable(userMapper.getUserByUsername(username)).orElseThrow(() -> new BusinessException("密码错误"));
        if (!usersDO.getUserPwd().equals(passwordDTO.getOldPassword())) {
            throw new BusinessException("密码错误");
        }
        usersDO.setUserPwd(passwordDTO.getNewPassword());
        Integer count = userMapper.updateUser(usersDO);
        if (count == 0) {
            throw new BusinessException("修改失败");
        }
        return Result.success();
    }
}
