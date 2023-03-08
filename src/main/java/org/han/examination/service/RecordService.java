package org.han.examination.service;

import jakarta.annotation.Resource;
import org.han.examination.exception.BusinessException;
import org.han.examination.mapper.*;
import org.han.examination.pojo.data.*;
import org.han.examination.pojo.dto.RecordDTO;
import org.han.examination.pojo.vo.StudentExamVO;
import org.han.examination.result.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RecordService {
    @Resource
    private StudentExamMapper studentExamMapper;
    @Resource
    private StudentSubjectMapper studentSubjectMapper;
    @Resource
    private ExamMapper examMapper;
    @Resource
    private PaperMapper paperMapper;
    @Resource
    private UserMapper userMapper;

    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public Result<Void> addRecord(RecordDTO recordDTO) {
        ExamDO examDO = examMapper.getExamById(recordDTO.getEid());
        List<PaperDO> paper = paperMapper.getPaperListByExamId(recordDTO.getEid());
        Integer singleNumber = examDO.getSingleNumber();
        Integer singleCore = examDO.getSingleCore();
        Integer multipleNumber = examDO.getMultipleNumber();
        Integer multipleCore = examDO.getMultipleCore();
        AtomicReference<Integer> score = new AtomicReference<>(0);
        List<StudentSubjectDO> studentSubjectDOList = recordDTO.getSubjectList().stream()
                .map(subject -> {
                    StudentSubjectDO studentSubjectDO = new StudentSubjectDO();
                    for (PaperDO paperDO : paper) {
                        if (paperDO.getSid().equals(subject.getSid())) {
                            if (paperDO.getStype() == 1) {
                                if (paperDO.getSkey().equals(subject.getSkey())) {
                                    score.updateAndGet(v -> v + singleCore);
                                }
                            } else {
                                List<String> list = Arrays.asList(subject.getSkey().split(","));
                                list.sort(String::compareTo);
                                subject.setSkey(String.join(",", list));
                                if (paperDO.getSkey().equals(subject.getSkey())) {
                                    score.updateAndGet(v -> v + multipleCore);
                                }
                            }
                            break;
                        }
                    }
                    studentSubjectDO.setStudentKey(subject.getSkey());
                    studentSubjectDO.setEid(recordDTO.getEid());
                    studentSubjectDO.setUserId(recordDTO.getUserId());
                    studentSubjectDO.setSid(subject.getSid());
                    return studentSubjectDO;
                })
                .toList();

        StudentExamDO studentExamDO = new StudentExamDO();
        studentExamDO.setUserId(recordDTO.getUserId());
        studentExamDO.setEid(recordDTO.getEid());
        studentExamDO.setScore(score.get());
        studentExamDO.setPname(examDO.getPname());
        studentExamDO.setZscore(singleNumber * singleCore + multipleCore * multipleNumber);
        studentExamDO.setTjTime(new Date());
        studentExamDO.setClassId(recordDTO.getClassId());
        Integer count = studentExamMapper.addStudentExam(studentExamDO);
        if (count == 0) {
            throw new BusinessException("添加失败");
        }
        studentSubjectDOList.forEach(studentSubjectDO -> studentSubjectDO.setSeid(studentExamDO.getSeid()));
        if (studentSubjectDOList.size() != 0) {
            count = studentSubjectMapper.addStudentSubject(studentSubjectDOList);
            if (count == 0) {
                throw new BusinessException("添加失败");
            }
        }

        return Result.success();
    }

    public Result<List<StudentExamVO>> getStudentRecordList(Integer id) {
        List<StudentExamVO> studentExamVOList = studentExamMapper.getStudentExamListByUserId(id)
                .stream()
                .map(studentExamDO -> {
                    StudentExamVO studentExamVO = new StudentExamVO();
                    BeanUtils.copyProperties(studentExamDO, studentExamVO);
                    studentExamVO.setTjTime(formatter.format(studentExamDO.getTjTime()));
                    return studentExamVO;
                })
                .toList();
        return Result.success(studentExamVOList);
    }

    public Result<List<StudentExamVO>> getStudentRecordListByClassId(Integer id) {
        List<StudentExamDO> studentExamDOList = studentExamMapper.getStudentExamListByClassId(id);
        List<Integer> list = studentExamDOList.stream().map(StudentExamDO::getUserId)
                .toList();
        List<UsersDO> userList = userMapper.getUserListByIdList(list);
        List<StudentExamVO> result = studentExamDOList.stream()
                .map(studentExamDO -> {
                    StudentExamVO studentExamVO = new StudentExamVO();
                    BeanUtils.copyProperties(studentExamDO, studentExamVO);
                    studentExamVO.setTjTime(formatter.format(studentExamDO.getTjTime()));
                    for (UsersDO usersDO : userList) {
                        if (usersDO.getUserId().equals(studentExamDO.getUserId())) {
                            studentExamVO.setUsername(usersDO.getUsername());
                            studentExamVO.setTrueName(usersDO.getTrueName());
                            break;
                        }
                    }
                    return studentExamVO;
                })
                .toList();
        return Result.success(result);
    }
}
