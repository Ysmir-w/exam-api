package org.han.examination.mapper;

import org.apache.ibatis.annotations.*;
import org.han.examination.pojo.data.ExamDO;

import java.util.List;

@Mapper
public interface ExamMapper {
    @Insert("insert into exam values(null,#{pname},#{cno},#{userId},#{classId},#{singleNumber},#{singleCore},#{multipleNumber},#{multipleCore},#{examDate},#{examTime},#{testTime})")
    @Options(useGeneratedKeys = true, keyProperty = "eid")
    Integer addExam(ExamDO examDO);

    @Select("select eid, pname, cno, userid, classid, singlenumber, singlecore, multiplenumber, multiplecore, examdate, examtime, testtime from exam where classId=#{id}")
    List<ExamDO> getExamListByClassId(Integer id);

    @Select("select eid, pname, cno, userid, classid, singlenumber, singlecore, multiplenumber, multiplecore, examdate, examtime, testtime from exam where eid=#{id}")
    ExamDO getExamById(Integer id);

    @Delete("delete from exam where eid = #{id}")
    Integer deleteExamById(Integer id);

}
