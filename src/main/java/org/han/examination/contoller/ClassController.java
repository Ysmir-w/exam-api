package org.han.examination.contoller;

import jakarta.annotation.Resource;
import org.han.examination.log.annotation.LogMarker;
import org.han.examination.pojo.dto.ClassDTO;
import org.han.examination.pojo.vo.ClassVO;
import org.han.examination.result.Result;
import org.han.examination.service.ClassService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ClassController {

    @Resource
    private ClassService classService;

    @PostMapping("class")
    @LogMarker
    public Result<Void> addClass(@RequestBody ClassDTO classDTO) {
        return classService.addClass(classDTO.getClassName());
    }

    @DeleteMapping("class/{id}")
    @LogMarker
    public Result<Void> deleteClass(@PathVariable Integer id) {
        return classService.deleteClass(id);
    }

    @PutMapping("class/{id}")
    @LogMarker
    public Result<Void> updateClass(@RequestBody ClassDTO classDTO, @PathVariable Integer id) {
        classDTO.setClassId(id);
        return classService.updateClass(classDTO);
    }

    @GetMapping("class/{id}")
    @LogMarker
    public Result<ClassVO> getClassById(@PathVariable Integer id) {
        return classService.getClassById(id);
    }

    @GetMapping("class/page/{page}/size/{size}")
    @LogMarker
    public Result<Map<String, Object>> getClassList(@PathVariable Integer page,@PathVariable Integer size) {
        return classService.getClassList(page, size);
    }
}
