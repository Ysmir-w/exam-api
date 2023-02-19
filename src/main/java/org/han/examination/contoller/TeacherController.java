package org.han.examination.contoller;

import jakarta.annotation.Resource;
import org.han.examination.log.annotation.LogMarker;
import org.han.examination.pojo.dto.TeacherDTO;
import org.han.examination.pojo.vo.TeacherVO;
import org.han.examination.result.Result;
import org.han.examination.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @LogMarker
    @PostMapping("teacher")
    public Result<Void> addTeacher(@RequestBody TeacherDTO teacherDTO) {
        return teacherService.addTeacher(teacherDTO);
    }

    @LogMarker
    @DeleteMapping("teacher/{id}")
    public Result<Void> deleteTeacher(@PathVariable Integer id) {
        return teacherService.deleteTeacher(id);
    }

    @LogMarker
    @PutMapping("teacher/{id}")
    public Result<Void> updateTeacher(@PathVariable Integer id, @RequestBody TeacherDTO teacherDTO) {
        teacherDTO.setUserId(id);
        return teacherService.updateTeacher(teacherDTO);
    }

    @LogMarker
    @GetMapping("teacher/{id}")
    public Result<TeacherVO> getTeacher(@PathVariable Integer id) {
        return teacherService.getTeacherById(id);
    }

    @LogMarker
    @GetMapping("teacher/page/{page}/size/{size}")
    public Result<Map<String, Object>> getTeacherList(@PathVariable Integer page, @PathVariable Integer size) {
        return teacherService.getTeacherList(page, size);
    }
}
