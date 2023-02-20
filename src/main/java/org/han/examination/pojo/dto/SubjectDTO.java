package org.han.examination.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectDTO {
    private Integer sid;
    private Integer cno;
    private Integer sType;
    private String sContent;
    private String sa;
    private String sb;
    private String sc;
    private String sd;
    private String sKey;
}
