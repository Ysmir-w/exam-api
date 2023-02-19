package org.han.examination.contoller;

import jakarta.annotation.Resource;
import org.han.examination.log.annotation.LogMarker;
import org.han.examination.pojo.dto.CourseDTO;
import org.han.examination.pojo.vo.CourseVO;
import org.han.examination.result.Result;
import org.han.examination.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CourseController {

    @Resource
    private CourseService courseService;

    @PostMapping("course")
    @LogMarker
    public Result<Void> addCourse(@RequestBody CourseDTO course) {
        return courseService.addCourse(course.getCname());
    }

    @DeleteMapping("course/{id}")
    @LogMarker
    public Result<Void> deleteCourse(@PathVariable Integer id) {
        return courseService.deleteCourse(id);
    }

    @PutMapping("course/{id}")
    @LogMarker
    public Result<Void> updateCourse(@RequestBody CourseDTO course, @PathVariable Integer id) {
        course.setCno(id);
        return courseService.updateCourse(course);
    }

    @GetMapping("course/{id}")
    @LogMarker
    public Result<CourseVO> getCourseById(@PathVariable Integer id) {
        return courseService.getCourseById(id);
    }

    @GetMapping("course/page/{page}/size/{size}")
    @LogMarker
    public Result<Map<String, Object>> getCourseList(@PathVariable Integer page,@PathVariable Integer size) {
        return courseService.getCourseList(page, size);
    }
}
