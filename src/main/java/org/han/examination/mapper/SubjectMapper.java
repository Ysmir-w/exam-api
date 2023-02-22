package org.han.examination.mapper;

import org.apache.ibatis.annotations.*;
import org.han.examination.pojo.data.SubjectDO;

import java.util.List;

@Mapper
public interface SubjectMapper {
    @Insert("insert into subject values(null,#{cno},#{stype},#{scontent},#{sa},#{sb},#{sc},#{sd},#{skey})")
    Integer addSubject(SubjectDO subject);

    @Delete("delete from subject where sid=#{id}")
    Integer deleteSubject(Integer id);

    @Update("update subject set cno=#{cno},scontent=#{scontent},sa=#{sa},sb=#{sb},sc=#{sc},sd=#{sd},skey=#{skey} where sid=#{sid}")
    Integer updateSubject(SubjectDO subject);

    @Select("select sid, cno, stype, scontent, sa, sb, sc, sd, skey from subject where sid=#{sid}")
    SubjectDO getSubjectById(Integer id);

    @Select("select sid, cno, stype, scontent, sa, sb, sc, sd, skey from subject where stype=#{stype} limit #{offset},#{count}")
    List<SubjectDO> getSubjectList(@Param("offset") Integer offset, @Param("count") Integer count, @Param("stype") Integer stype);

    @Select("select count(*) from subject where stype = #{stype}")
    Integer getSubjectListCount(Integer stype);
}
