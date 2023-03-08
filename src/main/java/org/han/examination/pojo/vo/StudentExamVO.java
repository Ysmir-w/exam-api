package org.han.examination.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentExamVO {
    private String username;
    private String trueName;
    private String pname;
    private Integer zscore;
    private Integer score;
    private String tjTime;

}
