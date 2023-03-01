package org.han.examination.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.han.examination.pojo.data.ExamDO;

@Mapper
public interface ExamMapper {
    @Insert("insert into exam values(null,#{pname},#{cno},#{userId},#{classId},#{singleNumber},#{singleCore},#{multipleNumber},#{multipleCore},#{examDate},#{examTime},#{testTime})")
    @Options(useGeneratedKeys = true,keyProperty = "eid")
    Integer addExam(ExamDO examDO);

}
