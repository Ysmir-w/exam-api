package org.han.examination.service;

import jakarta.annotation.Resource;
import org.han.examination.enumerate.ErrorEnum;
import org.han.examination.exception.BusinessException;
import org.han.examination.mapper.CourseMapper;
import org.han.examination.pojo.data.CourseDO;
import org.han.examination.pojo.dto.CourseDTO;
import org.han.examination.pojo.vo.CourseVO;
import org.han.examination.result.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Resource
    private CourseMapper courseMapper;

    public Result<Void> addCourse(String cname) {
        Integer count = courseMapper.isCourseExist(cname);
        if (count > 0) {
            throw new BusinessException("该课程已存在");
        }
        count = courseMapper.addCourse(cname);
        if (count == 0) {
            throw new BusinessException(ErrorEnum.ERROR);
        }
        return Result.success();
    }

    public Result<Void> deleteCourse(Integer id) {
        Integer count = courseMapper.deleteCourse(id);
        if (count == 0) {
            throw new BusinessException("删除失败");
        }
        return Result.success();
    }

    public Result<Void> updateCourse(CourseDTO courseDTO) {
        CourseDO courseDO = new CourseDO();
        BeanUtils.copyProperties(courseDTO, courseDO);
        Integer count = courseMapper.isCourseExistOnUpdate(courseDO);
        if (count > 0) {
            throw new BusinessException("该课程已存在");
        }
        count = courseMapper.updateCourse(courseDO);
        if (count == 0) {
            throw new BusinessException(ErrorEnum.ERROR);
        }
        return Result.success();
    }

    public Result<CourseVO> getCourseById(Integer id) {
        CourseDO courseDO = courseMapper.getCourseById(id);
        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(courseDO, courseVO);
        return Result.success(courseVO);
    }

    public Result<Map<String, Object>> getCourseList(Integer page,Integer size) {
        System.out.println((page-1)*size);
        List<CourseVO> courseList = courseMapper.getCourseList((page - 1) * size, size)
                .stream()
                .map(courseDO -> {
                    CourseVO courseVO = new CourseVO();
                    BeanUtils.copyProperties(courseDO, courseVO);
                    return courseVO;
                })
                .toList();
        Integer count = courseMapper.getCourseCount();
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("data", courseList);
        return Result.success(result);
    }
}
