package org.han.examination.service;

import jakarta.annotation.Resource;
import org.han.examination.mapper.CourseMapper;
import org.han.examination.mapper.SubjectMapper;
import org.han.examination.pojo.data.CourseDO;
import org.han.examination.pojo.data.SubjectDO;
import org.han.examination.pojo.dto.SubjectDTO;
import org.han.examination.pojo.vo.SubjectVO;
import org.han.examination.result.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubjectService {
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private SubjectMapper subjectMapper;

    public Result<Void> addSubject(SubjectDTO subjectDTO) {
        SubjectDO subjectDO = new SubjectDO();
        BeanUtils.copyProperties(subjectDTO, subjectDO);
        Integer count = subjectMapper.addSubject(subjectDO);
        return count != 0 ? Result.success() : Result.error();
    }

    public Result<Void> deleteSubject(Integer id) {
        Integer count = subjectMapper.deleteSubject(id);
        return count != 0 ? Result.success() : Result.error();
    }

    public Result<Void> updateSubject(SubjectDTO subjectDTO) {
        SubjectDO subjectDO = new SubjectDO();
        BeanUtils.copyProperties(subjectDTO, subjectDO);
        Integer count = subjectMapper.updateSubject(subjectDO);
        return count != 0 ? Result.success() : Result.error();
    }

    public Result<SubjectVO> getSubjectById(Integer id) {
        SubjectDO subjectDO = subjectMapper.getSubjectById(id);
        SubjectVO subjectVO = new SubjectVO();
        BeanUtils.copyProperties(subjectDO, subjectVO);
        subjectVO.setCno(String.valueOf(subjectDO.getCno()));
        return Result.success(subjectVO);
    }

    public Result<Map<String, Object>> getSubjectList(Integer page, Integer size, Integer sType) {
        List<SubjectDO> subjectDOList = subjectMapper.getSubjectList((page - 1) * size, size, sType);
        List<Integer> cnoList = subjectDOList.stream().map(SubjectDO::getCno).toList();
        List<CourseDO> courseDOList = courseMapper.getCourseListByIdList(cnoList);
        List<SubjectVO> subjectVOList = subjectDOList
                .stream()
                .map(subjectDO -> {
                    SubjectVO subjectVO = new SubjectVO();
                    BeanUtils.copyProperties(subjectDO, subjectVO);
                    for (CourseDO courseDO : courseDOList) {
                        if (subjectDO.getCno().equals(courseDO.getCno())) {
                            subjectVO.setCno(String.valueOf(subjectDO.getCno()));
                            subjectVO.setCname(courseDO.getCname());
                            break;
                        }
                    }
                    return subjectVO;
                })
                .toList();
        Integer count = subjectMapper.getSubjectListCount(sType);
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("data", subjectVOList);
        return Result.success(result);
    }
}
