package org.han.examination.contoller;

import jakarta.annotation.Resource;
import org.han.examination.log.annotation.LogMarker;
import org.han.examination.pojo.dto.SubjectDTO;
import org.han.examination.pojo.vo.SubjectVO;
import org.han.examination.result.Result;
import org.han.examination.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SubjectController {
    @Resource
    private SubjectService subjectService;

    @LogMarker
    @PostMapping("subject")
    public Result<Void> addSubject(@RequestBody SubjectDTO subjectDTO) {
        return subjectService.addSubject(subjectDTO);
    }

    @LogMarker
    @DeleteMapping("subject/{id}")
    public Result<Void> deleteSubject(@PathVariable Integer id) {
        return subjectService.deleteSubject(id);
    }

    @LogMarker
    @PutMapping("subject/{id}")
    public Result<Void> updateSubject(@PathVariable Integer id, @RequestBody SubjectDTO subjectDTO) {
        subjectDTO.setSid(id);
        return subjectService.updateSubject(subjectDTO);
    }

    @LogMarker
    @GetMapping("subject/{id}")
    public Result<SubjectVO> getSubject(@PathVariable Integer id) {
        return subjectService.getSubjectById(id);
    }

    @LogMarker
    @GetMapping("subject/sType/{sType}/page/{page}/size/{size}")
    public Result<Map<String, Object>> getSubjectList(@PathVariable Integer page, @PathVariable Integer size, @PathVariable Integer sType) {
        return subjectService.getSubjectList(page, size, sType);
    }
}
