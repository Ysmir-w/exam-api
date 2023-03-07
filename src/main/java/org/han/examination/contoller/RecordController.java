package org.han.examination.contoller;

import jakarta.annotation.Resource;
import org.han.examination.log.annotation.LogMarker;
import org.han.examination.pojo.dto.RecordDTO;
import org.han.examination.result.Result;
import org.han.examination.service.RecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecordController {
    @Resource
    private RecordService recordService;

    @PostMapping("record")
    @LogMarker
    public Result<Void> addRecord(@RequestBody RecordDTO recordDTO) {
        return recordService.addRecord(recordDTO);
    }
}
