package org.han.examination.mapper;

import org.apache.ibatis.annotations.*;
import org.han.examination.pojo.data.CourseDO;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Insert("insert into course values(null,#{cname})")
    Integer addCourse(String cname);

    @Delete("delete from course where cno = #{id}")
    Integer deleteCourse(Integer id);

    @Update("update course set cname = #{cname} where cno = #{cno}")
    Integer updateCourse(CourseDO course);

    @Select("select cno,cname from course where cno = #{id}")
    CourseDO getCourseById(Integer id);

    @Select("select cno,cname from course limit #{offset},#{count}")
    List<CourseDO> getCourseList(Integer offset, Integer count);

    @Select("select count(*) from course")
    Integer getCourseCount();

    @Select("select count(*) from course where cname = #{cname}")
    Integer isCourseExist(String cname);

    @Select("select count(*) from course where cname = #{cname} and cno != #{cno}")
    Integer isCourseExistOnUpdate(CourseDO courseDO);

    @Select("""
            <script>
                select cno,cname from course
                <where>
                    <if test='idList != null and idList.size() != 0'>
                        cno in
                        <foreach collection='idList' open='(' item='id' separator=',' close=')'>
                            #{id}
                        </foreach>
                    </if>
                </where>
            </script>
            """)
    List<CourseDO> getCourseListByIdList(List<Integer> idList);

    @Select("select cno,cname from course")
    List<CourseDO> getCourseListAll();
}
