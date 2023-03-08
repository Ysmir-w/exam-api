package org.han.examination.contoller;

import jakarta.annotation.Resource;
import org.han.examination.log.annotation.LogMarker;
import org.han.examination.pojo.dto.RecordDTO;
import org.han.examination.pojo.vo.StudentExamVO;
import org.han.examination.result.Result;
import org.han.examination.service.RecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordController {
    @Resource
    private RecordService recordService;

    @PostMapping("record")
    @LogMarker
    public Result<Void> addRecord(@RequestBody RecordDTO recordDTO) {
        return recordService.addRecord(recordDTO);
    }

    @GetMapping("record/{id}")
    @LogMarker
    public Result<List<StudentExamVO>> getStudentRecordList(@PathVariable("id") Integer id) {
        return recordService.getStudentRecordList(id);
    }

    @GetMapping("record/class/{id}")
    public Result<List<StudentExamVO>> getStudentRecordListByClassId(@PathVariable("id") Integer id) {
        return recordService.getStudentRecordListByClassId(id);
    }
}
