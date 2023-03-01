package org.han.examination.pojo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamDO {
    private Integer eid;
    private String pname;
    private Integer cno;
    private Integer userId;
    private Integer classId;
    private Integer singleNumber;
    private Integer singleCore;
    private Integer multipleNumber;
    private Integer multipleCore;
    private Date examDate;
    private Date examTime;
    private Integer testTime;
}
