package org.han.examination.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectVO {
    private Integer sid;
    private String cno;
    private String cName;
    private Integer sType;
    private String sContent;
    private String sa;
    private String sb;
    private String sc;
    private String sd;
    private String sKey;
}
