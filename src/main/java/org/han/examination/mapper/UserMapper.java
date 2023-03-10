package org.han.examination.mapper;

import org.apache.ibatis.annotations.*;
import org.han.examination.pojo.data.UsersDO;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into users(roleid, username, userpwd, truename, classid) values (#{roleId},#{username},#{userPwd},#{trueName},#{classId})")
    Integer addUser(UsersDO user);

    @Delete("delete from users where userid=#{id}")
    Integer deleteUser(Integer id);

    @Update("update users set roleid = #{roleId}, username = #{username}, truename = #{trueName}, classid = #{classId} where userid = #{userId}")
    Integer updateUser(UsersDO user);

    @Select("select userid,roleid,username,userpwd,truename,classid from users where userid = #{id}")
    UsersDO getUserById(Integer id);

    @Select("select userid,roleid,username,userpwd,truename,classid from users where username = #{username}")
    UsersDO getUserByUsername(String username);

    @Select("""
            <script>
                select userid,roleid,username,userpwd,truename,classid from users
                <where>
                    <if test='list != null and list.size() != 0'>
                        userid in
                        <foreach collection='list' open='(' item='id' separator=',' close=')'>
                            #{id}
                        </foreach>
                    </if>
                </where>
            </script>
            """)
    List<UsersDO> getUserListByIdList(List<Integer> list);

    @Select("select count(*) from users where username = #{username}")
    Integer isUserExistByUsername(String username);

    @Select("select count(*) from users where username = #{username} and userid != #{userId}")
    Integer isUserExistOnUpdate(UsersDO usersDO);

    @Select("""
            <script>
                select userid,roleid,username,userpwd,truename,classid from users
                <where>
                    <if test='user.userId != null'>
                        and userid=#{user.userId}
                    </if>
                    <if test='user.roleId != null'>
                        and roleid=#{user.roleId}
                    </if>
                    <if test='user.username != null and user.username != ""'>
                        and username like concat('%',#{user.username},'%')
                    </if>
                    <if test='user.trueName != null and user.trueName != ""'>
                        and truename like concat('%',#{trueName},'%')
                    </if>
                    <if test='user.classId != null and user.classId !=""'>
                        and classid = #{user.classId}
                    </if>
                </where>
                limit #{offset},#{count}
            </script>
            """)
    List<UsersDO> getUserList(@Param("user") UsersDO user, @Param("offset") Integer offset,@Param("count") Integer count);

    @Select("""
            <script>
                select count(*) from users
                <where>
                    <if test='userId != null'>
                        and userid=#{userId}
                    </if>
                    <if test='roleId != null'>
                        and roleid=#{roleId}
                    </if>
                    <if test='username != null and username != ""'>
                        and username like concat('%',#{username},'%')
                    </if>
                    <if test='trueName != null and trueName != ""'>
                        and truename like concat('%',#{trueName},'%')
                    </if>
                    <if test='classId != null and classId !=""'>
                        and classid = #{classId}
                    </if>
                </where>
            </script>
            """)
    Integer getUserListCount(UsersDO user);
}
