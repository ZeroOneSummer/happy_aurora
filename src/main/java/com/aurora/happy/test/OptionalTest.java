package com.aurora.happy.test;

import com.aurora.happy.pojo.User;

import java.util.Optional;

/**
 * Created by pijiang on 2021/3/18.
 * 容器对象Optional
 */
public class OptionalTest {

    public static void main(String[] args) {
        Integer value1 = null;
        Integer value2 = 10;
        Optional<Object> empty = Optional.empty();             //返回null对象
        Optional<Integer> op1 = Optional.ofNullable(value1);   //允许存null
        boolean present = op1.isPresent();                     //判断是否非空
        Integer isNullDefualt = op1.orElse(0);          //为null给默认值
        Optional<Integer> op2 = Optional.of(value2);           //null时抛异常
        Integer integer = op2.get();                           //取出里的值
        String name = Optional.of(new User("linda", 18))
                              .map(User::getName)
                              .orElseThrow(() -> new IllegalArgumentException("not exsists name!"));
    }
}