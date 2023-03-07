package org.han.examination.pojo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentSubjectDO {
    private Integer ssid;
    private Integer seid;
    private Integer userId;
    private Integer eid;
    private Integer sid;
    private String studentKey;
}
