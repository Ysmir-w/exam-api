package org.han.examination.contoller;

import jakarta.annotation.Resource;
import org.han.examination.log.annotation.LogMarker;
import org.han.examination.pojo.dto.ExamDTO;
import org.han.examination.pojo.vo.ExamVO;
import org.han.examination.result.Result;
import org.han.examination.service.ExamService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class ExamController {
    @Resource
    private ExamService examService;

    @PostMapping("exam")
    @LogMarker
    public Result<Void> addExam(@RequestBody ExamDTO examDTO) throws ParseException {
        return examService.addExam(examDTO);
    }

    @GetMapping("exam/class/{classId}")
    @LogMarker
    public Result<List<ExamVO>> getExamList(@PathVariable Integer classId) {
        return examService.getExamList(classId);
    }

    @GetMapping("exam/{id}")
    @LogMarker
    public Result<ExamVO> getExam(@PathVariable Integer id) {
        return examService.getExam(id);
    }
    @DeleteMapping("exam/{id}")
    @LogMarker
    public Result<Void> deleteExam(@PathVariable Integer id) {
        return examService.deleteExam(id);
    }
}
