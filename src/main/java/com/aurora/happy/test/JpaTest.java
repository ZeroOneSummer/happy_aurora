package com.aurora.happy.test;

import com.aurora.happy.mapper.UserMapper;
import com.aurora.happy.pojo.User;

public class JpaTest {

    public static void main(String[] args) throws Exception{
        int rows = new UserMapper().add(new User("linda", 20));
        System.out.println(rows);
    }
}