package org.han.examination.mapper;

import org.apache.ibatis.annotations.*;
import org.han.examination.pojo.data.PaperDO;

import java.util.List;

@Mapper
public interface PaperMapper {
    @Insert("""
            <script>
            insert into paper values
                <foreach collection='paperList' item='item' separator=','>
                    (null,#{item.eid},#{item.sid},#{item.cno},#{item.stype},#{item.scontent},#{item.sa},#{item.sb},#{item.sc},#{item.sd},#{item.skey})
                </foreach>
            </script>
            """)
    Integer addPaper(List<PaperDO> paperList);

    @Delete("delete from paper where eid=#{id}")
    void deletePaperByExamId(Integer id);

    @Select("select pid, eid, sid, cno, stype, scontent, sa, sb, sc, sd, skey from paper where eid = #{id}")
    List<PaperDO> getPaperListByExamId(Integer id);
}
