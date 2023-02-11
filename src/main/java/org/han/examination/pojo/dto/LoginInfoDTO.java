package org.han.examination.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginInfoDTO {
    private String username;
    private String userPwd;
    private Integer roleId;
}
