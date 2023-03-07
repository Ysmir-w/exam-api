package org.han.examination.pojo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentExamDO {
    private Integer seid;
    private Integer userId;
    private Integer classId;
    private Integer eid;
    private String pname;
    private Integer zscore;
    private Integer score;
    private Date tjTime;
}
