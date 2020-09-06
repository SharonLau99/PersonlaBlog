package org.sharon.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.sharon.demo.bean.User;

/**
 * @author sharon
 * @create 2020-08-30-9:37
 */
@Mapper
public interface UserMapper {
    //通过用户名和密码获得用户
    @Select("select * from t_user where username = #{username} and password = #{password}")
    public User getUserByUsernameAndPassword(String username, String password);

    //通过用户名获得用户
    @Select("select * from t_user where username = #{username}")
    public User getUserByUsername(String username);
}
