package com.aurora.happy.test;

import com.aurora.happy.mapper.JpaUserMapper;
import com.aurora.happy.pojo.User;
/**
 * Created by pijiang on 2021/3/17.
 * 子类泛型T取值
 */
public class JpaTest {

    public static void main(String[] args) throws Exception{
        int rows = new JpaUserMapper().add(new User(null,"xiaomi", 22));
        System.out.println(rows);
    }
}