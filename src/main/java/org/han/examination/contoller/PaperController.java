package org.han.examination.contoller;

import jakarta.annotation.Resource;
import org.han.examination.pojo.vo.PaperVO;
import org.han.examination.result.Result;
import org.han.examination.service.PaperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaperController {
    @Resource
    private PaperService paperService;

    @GetMapping("paper/exam/{id}")
    public Result<List<PaperVO>> getPaperInfo(@PathVariable Integer id) {
        return paperService.getPaperInfo(id);
    }
}
