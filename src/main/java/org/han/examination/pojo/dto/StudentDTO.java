package org.han.examination.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDTO {
    private Integer userId;
    private String username;
    private String trueName;
    private Integer classId;
}
