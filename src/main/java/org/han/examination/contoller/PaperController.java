package org.han.examination.contoller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.han.examination.log.annotation.LogMarker;
import org.han.examination.pojo.vo.LoginVO;
import org.han.examination.pojo.vo.PaperVO;
import org.han.examination.result.Result;
import org.han.examination.service.PaperService;
import org.han.examination.utils.JedisUtil;
import org.han.examination.utils.JsonUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Objects;

@RestController
public class PaperController {
    @Resource
    private PaperService paperService;

    @Resource
    private JedisUtil jedisUtil;

    @Resource
    private JsonUtil jsonUtil;

    @GetMapping("paper/exam/{id}")
    @LogMarker
    public Result<List<PaperVO>> getPaperInfo(@PathVariable Integer id, HttpServletRequest request) {
        try (Jedis jedis = jedisUtil.getJedis()) {
            String username = request.getHeader("username");
            LoginVO loginInfo = jsonUtil.deserialize(jedis.get("exam:login:" + username), LoginVO.class);
            Result<List<PaperVO>> paperInfo = paperService.getPaperInfo(id);
            if (!Objects.equals(loginInfo.getRoleId(), "1")) {
                paperInfo.getData().forEach(paperVO -> paperVO.setSkey(""));
            }
            return paperInfo;
        }

    }

}
