package org.han.examination.pojo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsersDO {
    private Integer userId;
    private Integer roleId;
    private String username;
    private String userPwd;
    private String trueName;
    private String classId;
}
