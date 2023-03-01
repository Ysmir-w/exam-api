package org.han.examination.service;

import jakarta.annotation.Resource;
import org.han.examination.exception.BusinessException;
import org.han.examination.mapper.ExamMapper;
import org.han.examination.mapper.PaperMapper;
import org.han.examination.mapper.SubjectMapper;
import org.han.examination.pojo.data.ExamDO;
import org.han.examination.pojo.data.PaperDO;
import org.han.examination.pojo.data.SubjectDO;
import org.han.examination.pojo.dto.ExamDTO;
import org.han.examination.result.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExamService {

    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    @Resource
    private PaperMapper paperMapper;
    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private ExamMapper examMapper;

    @Transactional
    public Result<Void> addExam(ExamDTO examDTO) throws ParseException {
        Integer singleNumber = examDTO.getSingleNumber();
        Integer multipleNumber = examDTO.getMultipleNumber();
        List<Integer> singleSubjectIdList = subjectMapper.getSubjectIdListBySType(1);
        List<Integer> multipleSubjectIdList = subjectMapper.getSubjectIdListBySType(2);
        singleSubjectIdList = getRandomSubjectIdList(singleSubjectIdList, singleNumber);
        multipleSubjectIdList = getRandomSubjectIdList(multipleSubjectIdList, multipleNumber);

        List<SubjectDO> subjectList = subjectMapper.getSubjectListByIdList(singleSubjectIdList);
        subjectList.addAll(subjectMapper.getSubjectListByIdList(multipleSubjectIdList));

        ExamDO examDO = new ExamDO();
        BeanUtils.copyProperties(examDTO, examDO);
        examDO.setCno(Integer.parseInt(examDTO.getCno()));
        examDO.setClassId(Integer.parseInt(examDTO.getClassId()));
        examDO.setExamDate(formatter.parse(examDTO.getExamDate().substring(0, 9)));
        examDO.setExamTime(formatter.parse(examDTO.getExamTime().substring(0, 9)));

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
}
