package org.han.examination.contoller;

import jakarta.annotation.Resource;
import org.han.examination.pojo.dto.LoginInfoDTO;
import org.han.examination.pojo.vo.LoginResultVO;
import org.han.examination.result.Result;
import org.han.examination.service.LoginService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @PostMapping("login")
    public Result<LoginResultVO> login(@RequestBody @Validated LoginInfoDTO loginInfo) {
        return loginService.login(loginInfo);
    }
}
