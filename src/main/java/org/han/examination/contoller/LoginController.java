package org.han.examination.contoller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.han.examination.log.annotation.LogMarker;
import org.han.examination.pojo.dto.LoginInfoDTO;
import org.han.examination.pojo.dto.PasswordDTO;
import org.han.examination.pojo.vo.LoginVO;
import org.han.examination.result.Result;
import org.han.examination.service.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @PostMapping("login")
    @LogMarker
    public Result<LoginVO> login(@RequestBody LoginInfoDTO loginInfoDTO) {
        return loginService.login(loginInfoDTO);
    }

    @DeleteMapping("logout")
    @LogMarker
    public Result<Void> logout(HttpServletRequest request) {
        String username = request.getHeader("username");
        return loginService.logout(username);
    }

    @PutMapping("password")
    @LogMarker
    public Result<Void> updatePassword(HttpServletRequest request,@RequestBody PasswordDTO passwordDTO) {
        String username = request.getHeader("username");
        return loginService.updatePassword(username,passwordDTO);
    }
}
