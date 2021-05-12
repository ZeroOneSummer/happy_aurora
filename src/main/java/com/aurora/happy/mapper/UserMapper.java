package com.aurora.happy.mapper;

import com.aurora.happy.pojo.User;
import org.apache.ibatis.annotations.Update;

/**
 * Created by pijiang on 2021/5/12.
 */
public interface UserMapper {

    int addUser(User user);

    @Update("update t_jpa_user set age = #{age, jdbcType=INTEGER}, `name` = #{name, jdbcType=VARCHAR} where id = #{id, jdbcType=BIGINT}")
    int updateUser(User user);
}