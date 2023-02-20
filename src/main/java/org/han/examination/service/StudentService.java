package org.han.examination.service;

import jakarta.annotation.Resource;
import org.han.examination.exception.BusinessException;
import org.han.examination.mapper.ClassMapper;
import org.han.examination.mapper.UserMapper;
import org.han.examination.pojo.data.ClassDO;
import org.han.examination.pojo.data.UsersDO;
import org.han.examination.pojo.dto.StudentDTO;
import org.han.examination.pojo.vo.LoginVO;
import org.han.examination.pojo.vo.StudentVO;
import org.han.examination.result.Result;
import org.han.examination.utils.JedisUtil;
import org.han.examination.utils.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    @Resource
    private ClassMapper classMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private JedisUtil jedisUtil;
    @Resource
    private JsonUtil jsonUtil;

    public Result<Void> addStudent(StudentDTO studentDTO,String key) {
        UsersDO usersDO = new UsersDO();
        usersDO.setUserPwd("123456");
        usersDO.setUsername(studentDTO.getUsername());
        usersDO.setTrueName(studentDTO.getTrueName());
        usersDO.setRoleId(2);
        usersDO.setClassId(getClassId(key));

        Integer count = userMapper.isUserExistByUsername(studentDTO.getUsername());
        if (count > 0) {
            throw new BusinessException("该学号已存在");
        }
        count = userMapper.addUser(usersDO);
        if (count == 0) {
            throw new BusinessException("添加失败");
        }
        return Result.success();
    }

    public Result<Void> deleteStudent(Integer id) {
        UsersDO usersDO = userMapper.getUserById(id);
        if (usersDO.getRoleId() != 1) {
            throw new BusinessException("该用户不是学生");
        }
        Integer count = userMapper.deleteUser(id);
        if (count == 0) {
            throw new BusinessException("删除失败");
        }
        return Result.success();
    }

    public Result<Void> updateStudent(StudentDTO studentDTO, String key) {
        UsersDO usersDO = new UsersDO();
        usersDO.setUserId(studentDTO.getUserId());
        usersDO.setUsername(studentDTO.getUsername());
        usersDO.setTrueName(studentDTO.getTrueName());
        usersDO.setClassId(getClassId(key));
        usersDO.setRoleId(2);
        Integer count = userMapper.isUserExistOnUpdate(usersDO);
        if (count > 0)
            throw new BusinessException("该学号已存在");

        count = userMapper.updateUser(usersDO);
        if (count == 0) {
            throw new BusinessException("更新失败");
        }
        return Result.success();
    }

    public Result<StudentVO> getStudentById(Integer id) {
        UsersDO usersDO = userMapper.getUserById(id);
        ClassDO classDO = classMapper.getClassById(usersDO.getClassId());
        StudentVO studentVO = new StudentVO();
        BeanUtils.copyProperties(usersDO, studentVO);
        studentVO.setClassId(String.valueOf(classDO.getClassId()));
        studentVO.setClassName(classDO.getClassName());
        return Result.success(studentVO);
    }

    public Result<Map<String, Object>> getStudentList(Integer page, Integer size, Integer classId) {
        UsersDO usersDO = new UsersDO();
        usersDO.setRoleId(2);
        usersDO.setClassId(classId);
        List<UsersDO> userList = userMapper.getUserList(usersDO, (page - 1) * size, size);
        List<Integer> classIdList = userList.stream().map(UsersDO::getClassId).toList();
        List<ClassDO> classList = classMapper.getClassListByIdList(classIdList);
        List<StudentVO> studentVOList = userList.stream()
                .map(user -> {
                    StudentVO studentVO = new StudentVO();
                    BeanUtils.copyProperties(user, studentVO);
                    for (ClassDO classDO : classList) {
                        if (user.getClassId().equals(classDO.getClassId())) {
                            studentVO.setClassName(classDO.getClassName());
                            studentVO.setClassId(String.valueOf(classDO.getClassId()));
                            break;
                        }
                    }
                    return studentVO;
                })
                .toList();
        Integer count = userMapper.getUserListCount(usersDO);
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("data", studentVOList);
        return Result.success(result);
    }

    private Integer getClassId(String key) {
        try (Jedis jedis = jedisUtil.getJedis()) {
            LoginVO loginInfo = jsonUtil.deserialize(jedis.get(key), LoginVO.class);
            return Integer.parseInt(loginInfo.getClassId());
        }
    }
}
