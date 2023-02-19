package org.han.examination.service;

import jakarta.annotation.Resource;
import org.han.examination.enumerate.ErrorEnum;
import org.han.examination.exception.BusinessException;
import org.han.examination.mapper.ClassMapper;
import org.han.examination.pojo.data.ClassDO;
import org.han.examination.pojo.dto.ClassDTO;
import org.han.examination.pojo.vo.ClassVO;
import org.han.examination.pojo.vo.OptionVO;
import org.han.examination.result.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassService {

    @Resource
    private ClassMapper classMapper;

    public Result<Void> addClass(String className) {
        Integer count = classMapper.isClassExist(className);
        if (count > 0) {
            throw new BusinessException("该课程已存在");
        }
        count = classMapper.addClass(className);
        if (count == 0) {
            throw new BusinessException(ErrorEnum.ERROR);
        }
        return Result.success();
    }

    public Result<Void> deleteClass(Integer id) {
        Integer count = classMapper.deleteClass(id);
        if (count == 0) {
            throw new BusinessException("删除失败");
        }
        return Result.success();
    }

    public Result<Void> updateClass(ClassDTO classDTO) {
        ClassDO classDO = new ClassDO();
        BeanUtils.copyProperties(classDTO, classDO);
        Integer count = classMapper.isClassExistOnUpdate(classDO);
        if (count > 0) {
            throw new BusinessException("该课程已存在");
        }
        count = classMapper.updateClass(classDO);
        if (count == 0) {
            throw new BusinessException(ErrorEnum.ERROR);
        }
        return Result.success();
    }

    public Result<ClassVO> getClassById(Integer id) {
        ClassDO classDO = classMapper.getClassById(id);
        ClassVO classVO = new ClassVO();
        BeanUtils.copyProperties(classDO, classVO);
        return Result.success(classVO);
    }

    public Result<Map<String, Object>> getClassList(Integer page,Integer size) {
        System.out.println((page-1)*size);
        List<ClassVO> classList = classMapper.getClassList((page - 1) * size, size)
                .stream()
                .map(classDO -> {
                    ClassVO classVO = new ClassVO();
                    BeanUtils.copyProperties(classDO, classVO);
                    return classVO;
                })
                .toList();
        Integer count = classMapper.getClassCount();
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("data", classList);
        return Result.success(result);
    }

    public Result<List<OptionVO>> getClassOptionList() {
        List<OptionVO> result = classMapper.getAllClassList().stream()
                .map(classDO -> {
                    OptionVO optionVO = new OptionVO();
                    optionVO.setLabel(classDO.getClassName());
                    optionVO.setValue(String.valueOf(classDO.getClassId()));
                    return optionVO;
                })
                .toList();
        return Result.success(result);
    }
}
