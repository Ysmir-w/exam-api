package org.han.examination.service;

import jakarta.annotation.Resource;
import org.han.examination.exception.BusinessException;
import org.han.examination.mapper.*;
import org.han.examination.pojo.data.CourseDO;
import org.han.examination.pojo.data.ExamDO;
import org.han.examination.pojo.data.PaperDO;
import org.han.examination.pojo.data.SubjectDO;
import org.han.examination.pojo.dto.ExamDTO;
import org.han.examination.pojo.vo.ExamVO;
import org.han.examination.result.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExamService {

    private final static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    @Resource
    private PaperMapper paperMapper;
    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private ExamMapper examMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private StudentExamMapper studentExamMapper;

    @Transactional
    public Result<Void> addExam(ExamDTO examDTO) throws ParseException {
        Integer singleNumber = examDTO.getSingleNumber();
        Integer multipleNumber = examDTO.getMultipleNumber();
        Integer cno = Integer.parseInt(examDTO.getCno());
        List<Integer> singleSubjectIdList = subjectMapper.getSubjectIdListBySTypeAndCno(1, cno);
        List<Integer> multipleSubjectIdList = subjectMapper.getSubjectIdListBySTypeAndCno(2, cno);
        singleSubjectIdList = getRandomSubjectIdList(singleSubjectIdList, singleNumber);
        multipleSubjectIdList = getRandomSubjectIdList(multipleSubjectIdList, multipleNumber);

        List<SubjectDO> subjectList = subjectMapper.getSubjectListByIdList(singleSubjectIdList);
        subjectList.addAll(subjectMapper.getSubjectListByIdList(multipleSubjectIdList));

        ExamDO examDO = new ExamDO();
        BeanUtils.copyProperties(examDTO, examDO);
        examDO.setCno(Integer.parseInt(examDTO.getCno()));
        examDO.setClassId(Integer.parseInt(examDTO.getClassId()));
        examDO.setExamDate(dateTimeFormatter.parse(examDTO.getExamDate()));
        examDO.setExamTime(dateTimeFormatter.parse(examDTO.getExamTime()));

        Integer count = examMapper.addExam(examDO);
        if (count == 0) {
            throw new BusinessException("添加失败");
        }
        List<PaperDO> paperDOList = subjectList.stream()
                .map(subjectDO -> {
                    PaperDO paperDO = new PaperDO();
                    paperDO.setEid(examDO.getEid());
                    paperDO.setSid(subjectDO.getSid());
                    paperDO.setCno(subjectDO.getCno());
                    paperDO.setStype(subjectDO.getStype());
                    paperDO.setScontent(subjectDO.getScontent());
                    paperDO.setSa(subjectDO.getSa());
                    paperDO.setSb(subjectDO.getSb());
                    paperDO.setSc(subjectDO.getSc());
                    paperDO.setSd(subjectDO.getSd());
                    paperDO.setSkey(subjectDO.getSkey());
                    return paperDO;
                }).toList();
        count = paperMapper.addPaper(paperDOList);
        if (count == 0) {
            throw new BusinessException("添加失败");
        }
        return Result.success();
    }

    public Result<List<ExamVO>> getExamList(Integer classId) {
        List<ExamDO> examDOList = examMapper.getExamListByClassId(classId);
        List<Integer> cnoList = examDOList.stream().map(ExamDO::getCno).toList();
        List<CourseDO> courseList = courseMapper.getCourseListByIdList(cnoList);
        List<ExamVO> result = examDOList.stream()
                .map(examDO -> {
                    ExamVO examVO = new ExamVO();
                    BeanUtils.copyProperties(examDO, examVO);
                    for (CourseDO courseDO : courseList) {
                        if (courseDO.getCno().equals(examDO.getCno())) {
                            examVO.setCname(courseDO.getCname());
                            break;
                        }
                    }
                    examVO.setExamDate(formatter.format(examDO.getExamDate()));
                    examVO.setExamTime(formatter.format(examDO.getExamTime()));
                    return examVO;
                })
                .toList();
        return Result.success(result);
    }

    @Transactional
    public Result<Void> deleteExam(Integer id) {
        paperMapper.deletePaperByExamId(id);
        Integer count = examMapper.deleteExamById(id);
        if (count == 0) {
            throw new BusinessException("删除失败");
        }
        return Result.success();
    }

    private List<Integer> getRandomSubjectIdList(List<Integer> list, int n) {
        Map<Integer, String> map = new HashMap<>();
        List<Integer> news = new ArrayList<>();
        if (list.size() <= n) {
            return list;
        } else {
            while (map.size() < n) {
                int random = (int) (Math.random() * list.size());
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    news.add(list.get(random));
                }
            }
            return news;
        }
    }


    public Result<ExamVO> getExam(Integer id) {
        ExamDO examDO = examMapper.getExamById(id);
        ExamVO examVO = new ExamVO();
        BeanUtils.copyProperties(examDO, examVO);
        return Result.success(examVO);
    }

    public Result<Void> getStatus(Integer eid, Integer userid) {
        ExamDO examDO = examMapper.getExamById(eid);
        Date startDate = examDO.getExamDate();
        Date endDate = examDO.getExamTime();
        endDate.setTime(endDate.getTime() + 1000 * 60 * 60 * 24);
        Date date = new Date();
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(date);
        if (date.before(startDate)) {
            throw new BusinessException("考试未开始");
        }
        if (date.after(endDate)) {
            throw new BusinessException("考试已结束");
        }
        Integer count = studentExamMapper.isStudentExamExist(userid, eid);
        if (count != 0) {
            throw new BusinessException("您已考过试");
        }
        return Result.success();
    }
}
