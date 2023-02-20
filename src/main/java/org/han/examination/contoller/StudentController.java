package org.han.examination.contoller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.han.examination.log.annotation.LogMarker;
import org.han.examination.pojo.dto.StudentDTO;
import org.han.examination.pojo.vo.StudentVO;
import org.han.examination.result.Result;
import org.han.examination.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class StudentController {
    @Resource
    private StudentService studentService;

    @LogMarker
    @PostMapping("student")
    public Result<Void> addStudent(@RequestBody StudentDTO studentDTO, HttpServletRequest request) {
        return studentService.addStudent(studentDTO, getTeacherKey(request));
    }

    @LogMarker
    @DeleteMapping("student/{id}")
    public Result<Void> deleteStudent(@PathVariable Integer id) {
        return studentService.deleteStudent(id);
    }

    @LogMarker
    @PutMapping("student/{id}")
    public Result<Void> updateStudent(@PathVariable Integer id, @RequestBody StudentDTO studentDTO, HttpServletRequest request) {
        studentDTO.setUserId(id);
        return studentService.updateStudent(studentDTO, getTeacherKey(request));
    }

    @LogMarker
    @GetMapping("student/{id}")
    public Result<StudentVO> getStudent(@PathVariable Integer id) {
        return studentService.getStudentById(id);
    }

    @LogMarker
    @GetMapping("student/class/{classId}/page/{page}/size/{size}")
    public Result<Map<String, Object>> getStudentList(@PathVariable Integer page, @PathVariable Integer size, @PathVariable Integer classId) {
        return studentService.getStudentList(page, size, classId);
    }

    private String getTeacherKey(HttpServletRequest request) {
        String key = request.getHeader("username");
        key = "exam:login:" + key;
        return key;
    }
}
