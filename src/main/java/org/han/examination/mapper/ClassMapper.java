package org.han.examination.mapper;

import org.apache.ibatis.annotations.*;
import org.han.examination.pojo.data.ClassDO;

import java.util.List;

@Mapper
public interface ClassMapper {
    @Insert("insert into pjClass values(null,#{className})")
    Integer addClass(String className);

    @Delete("delete from pjClass where classId = #{id}")
    Integer deleteClass(Integer id);

    @Update("update pjClass set className = #{className} where classId = #{classId}")
    Integer updateClass(ClassDO Class);

    @Select("select classId,className from pjClass where classId = #{id}")
    ClassDO getClassById(Integer id);

    @Select("select classId,className from pjClass")
    List<ClassDO> getAllClassList();
    @Select("select classId,className from pjClass limit #{offset},#{count}")
    List<ClassDO> getClassList(Integer offset, Integer count);

    @Select("select count(*) from pjClass")
    Integer getClassCount();

    @Select("select count(*) from pjClass where className = #{className}")
    Integer isClassExist(String className);

    @Select("select count(*) from pjClass where className = #{className} and classId != #{classId}")
    Integer isClassExistOnUpdate(ClassDO ClassDO);

    @Select("""
            <script>
                select classId,className from pjClass
                <where>
                    classId in
                    <foreach collection='idList' open='(' item='id' separator=',' close=')'>
                        #{id}
                    </foreach>
                </where>
            </script>
            """)
    List<ClassDO> getClassListByIdList(List<Integer> idList);
}
