package org.han.examination.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResultVO {
    private String token;
    private Integer id;
    private String username;
    private String name;
    private String email;
}
