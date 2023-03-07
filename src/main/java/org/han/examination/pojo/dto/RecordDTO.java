package org.han.examination.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecordDTO {
    private Integer eid;
    private Integer userId;
    private Integer classId;
    List<StudentSubjectDTO> subjectList;
}
