package org.han.examination.pojo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginResultDO {
    private String token;
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String phone;
}
