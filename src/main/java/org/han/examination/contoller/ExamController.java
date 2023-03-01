package org.han.examination.contoller;

import jakarta.annotation.Resource;
import org.han.examination.pojo.dto.ExamDTO;
import org.han.examination.result.Result;
import org.han.examination.service.ExamService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class ExamController {
    @Resource
    private ExamService examService;

    @PostMapping("exam")
    public Result<Void> addExam(@RequestBody ExamDTO examDTO) throws ParseException {
        return examService.addExam(examDTO);
    }
}
