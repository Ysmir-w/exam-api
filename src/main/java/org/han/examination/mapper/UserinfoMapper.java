package org.han.examination.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.han.examination.pojo.data.UserinfoDO;
import org.han.examination.pojo.dto.LoginInfoDTO;

import java.util.List;

@Mapper
public interface UserinfoMapper {
    @Select("select id,name,email,username,phone,password from userinfo")
    List<UserinfoDO> getAllUserInfo();

    @Select("select id,name,email,username,phone,password from userinfo where id = #{id}")
    UserinfoDO getUserInfoById(Integer id);

    @Select("select id,name,email,username,phone,password from userinfo where phone = #{phone} and password = #{password}")
    UserinfoDO getUserInfoByPhoneAndPassword(LoginInfoDTO loginInfo);
}
