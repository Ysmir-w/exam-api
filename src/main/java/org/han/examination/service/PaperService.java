package org.han.examination.service;

import jakarta.annotation.Resource;
import org.han.examination.mapper.PaperMapper;
import org.han.examination.pojo.vo.PaperVO;
import org.han.examination.result.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperService {
    @Resource
    private PaperMapper paperMapper;

    public Result<List<PaperVO>> getPaperInfo(Integer eid) {
        List<PaperVO> result = paperMapper.getPaperListByExamId(eid)
                .stream()
                .map(paperDO -> {
                    PaperVO paperVO = new PaperVO();
                    BeanUtils.copyProperties(paperDO, paperVO);
                    return paperVO;
                })
                .toList();
        return Result.success(result);
    }
}
