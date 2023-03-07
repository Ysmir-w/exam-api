package org.han.examination.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.han.examination.pojo.data.StudentExamDO;

import java.util.List;

@Mapper
public interface StudentExamMapper {
    @Insert("insert into studentexam values(null,#{userId},#{classId},#{eid},#{pname},#{zscore},#{score},#{tjTime})")
    @Options(useGeneratedKeys = true, keyProperty = "seid")
    Integer addStudentExam(StudentExamDO studentExamDO);


    @Select("select seid,userid,classid,eid,pname,zscore,score,tjtime from studentexam where userid=#{id}")
    List<StudentExamDO> getStudentExamListByUserId(Integer id);

    @Select("select seid,userid,classid,eid,pname,zscore,score,tjtime from studentexam where classid=#{id}")
    List<StudentExamDO> getStudentExamListByClassId(Integer id);

}
