package org.han.examination.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamDTO {
    private String pname;
    private String cno;
    private Integer userId;
    private String classId;
    private Integer singleNumber;
    private Integer singleCore;
    private Integer multipleNumber;
    private Integer multipleCore;
    private String examDate;
    private String examTime;
    private Integer testTime;
}
