package org.han.examination.service;

import org.han.examination.pojo.dto.LoginInfoDTO;
import org.han.examination.pojo.vo.LoginResultVO;
import org.han.examination.result.Result;

public interface LoginService {
    Result<LoginResultVO> login(LoginInfoDTO loginInfo);
}
