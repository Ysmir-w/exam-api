package org.han.examination.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamVO {
    private Integer eid;
    private String pname;
    private String cname;
    private Integer singleNumber;
    private Integer singleCore;
    private Integer multipleNumber;
    private Integer multipleCore;
    private String examDate;
    private String examTime;
    private Integer testTime;
}
