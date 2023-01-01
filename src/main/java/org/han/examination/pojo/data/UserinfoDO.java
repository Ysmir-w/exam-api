package org.han.examination.pojo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserinfoDO {
    private Integer id;
    private String name;
    private String email;
    private String username;
    private String phone;
    private String password;
}
