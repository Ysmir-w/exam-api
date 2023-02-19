package org.han.examination.service;

import jakarta.annotation.Resource;
import org.han.examination.exception.BusinessException;
import org.han.examination.mapper.ClassMapper;
import org.han.examination.mapper.UserMapper;
import org.han.examination.pojo.data.ClassDO;
import org.han.examination.pojo.data.UsersDO;
import org.han.examination.pojo.dto.TeacherDTO;
import org.han.examination.pojo.vo.TeacherVO;
import org.han.examination.result.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherService {
    @Resource
    private ClassMapper classMapper;
    @Resource
    private UserMapper userMapper;

    public Result<Void> addTeacher(TeacherDTO teacherDTO) {
        UsersDO usersDO = new UsersDO();
        usersDO.setUserPwd("123456");
        usersDO.setUsername(teacherDTO.getUsername());
        usersDO.setTrueName(teacherDTO.getTrueName());
        usersDO.setRoleId(1);
        usersDO.setClassId(teacherDTO.getClassId());

        Integer count = userMapper.isUserExistByUsername(teacherDTO.getUsername());
        if (count > 0) {
            throw new BusinessException("该工号已存在");
        }
        ClassDO classDO = classMapper.getClassById(teacherDTO.getClassId());
        if (classDO == null) {
            throw new BusinessException("该班级不存在");
        }

        count = userMapper.addUser(usersDO);
        if (count == 0) {
            throw new BusinessException("添加失败");
        }
        return Result.success();
    }

    public Result<Void> deleteTeacher(Integer id) {
        UsersDO usersDO = userMapper.getUserById(id);
        if (usersDO.getRoleId() != 1) {
            throw new BusinessException("该用户不是教师");
        }
        Integer count = userMapper.deleteUser(id);
        if (count == 0) {
            throw new BusinessException("删除失败");
        }
        return Result.success();
    }

    public Result<Void> updateTeacher(TeacherDTO teacherDTO) {
        UsersDO usersDO = new UsersDO();
        usersDO.setUserId(teacherDTO.getUserId());
        usersDO.setUsername(teacherDTO.getUsername());
        usersDO.setTrueName(teacherDTO.getTrueName());
        usersDO.setClassId(teacherDTO.getClassId());
        usersDO.setRoleId(1);
        Integer count = userMapper.isUserExistOnUpdate(usersDO);
        if(count>0)
            throw new BusinessException("该工号已存在");
        ClassDO classDO = classMapper.getClassById(teacherDTO.getClassId());
        if (classDO == null) {
            throw new BusinessException("该班级不存在");
        }

        count = userMapper.updateUser(usersDO);
        if (count == 0) {
            throw new BusinessException("更新失败");
        }
        return Result.success();
    }

    public Result<TeacherVO> getTeacherById(Integer id) {
        UsersDO usersDO = userMapper.getUserById(id);
        ClassDO classDO = classMapper.getClassById(usersDO.getClassId());
        TeacherVO teacherVO = new TeacherVO();
        BeanUtils.copyProperties(usersDO, teacherVO);
        teacherVO.setClassId(String.valueOf(classDO.getClassId()));
        teacherVO.setClassName(classDO.getClassName());
        return Result.success(teacherVO);
    }

    public Result<Map<String,Object>> getTeacherList(Integer page, Integer size) {
        UsersDO usersDO = new UsersDO();
        usersDO.setRoleId(1);
        List<UsersDO> userList = userMapper.getUserList(usersDO, (page - 1) * size, size);
        List<Integer> classIdList = userList.stream().map(UsersDO::getClassId).toList();
        List<ClassDO> classList = classMapper.getClassListByIdList(classIdList);
        List<TeacherVO> teacherVOList = userList.stream()
                .map(user -> {
                    TeacherVO teacherVO = new TeacherVO();
                    BeanUtils.copyProperties(user, teacherVO);
                    for (ClassDO classDO : classList) {
                        if (user.getClassId().equals(classDO.getClassId())) {
                            teacherVO.setClassName(classDO.getClassName());
                            teacherVO.setClassId(String.valueOf(classDO.getClassId()));
                            break;
                        }
                    }
                    return teacherVO;
                })
                .toList();
        Integer count = userMapper.getUserListCount(usersDO);
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("data", teacherVOList);
        return Result.success(result);
    }
}
