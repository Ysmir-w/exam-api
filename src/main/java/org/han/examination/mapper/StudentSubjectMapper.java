package org.han.examination.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.han.examination.pojo.data.StudentSubjectDO;

import java.util.List;

@Mapper
public interface StudentSubjectMapper {

    @Insert("""
            <script>
            insert into studentsubject values
                <foreach collection='list' item='item' separator=','>
                    (null,#{item.seid},#{item.userId},#{item.eid},#{item.sid},#{item.studentKey})
                </foreach>
            </script>
            """)
    Integer addStudentSubject(List<StudentSubjectDO> list);

    @Select("select ssid,seid,userid,eid,sid,studentkey from studentsubject where userid=#{userId} and examId=#{examId}")
    Integer getStudentSubjectByUserIdAndExamId(@Param("userId") Integer userId, @Param("examId") Integer examId);
}
