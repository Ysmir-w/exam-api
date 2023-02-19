package org.han.examination.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentVO {
    private Integer userId;
    private String username;
    private String trueName;
    private String classId;
    private String className;
}
