package org.han.examination.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaperVO {
    private Integer sid;
    private Integer stype;
    private String scontent;
    private String sa;
    private String sb;
    private String sc;
    private String sd;
    private String skey;
}
