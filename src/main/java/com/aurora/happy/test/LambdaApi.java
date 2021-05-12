package com.aurora.happy.test;

import com.aurora.happy.common.AuroraList;
import com.aurora.happy.pojo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pijiang on 2021/3/22.
 * 手写Lambda
 */
public class LambdaApi {

    public static void main(String[] args) {

        /*
        * 正式Lambda
        */
        List<User> list = new ArrayList<User>(){{
            add(new User(1001L,"张三", 18));
            add(new User(1002L,"李四", 19));
            add(new User(1003L,"王五", 20));
        }};
//        list.stream().filter(f -> f.getAge() > 18).map(new Function<User, String>() {
//            @Override
//            public String apply(User user) {
//                return user.getName();
//            }
//        });
        //=> 根据IDEA的提示简化成lambda
        List<String> strList = list.stream().filter(f -> f.getAge() > 18).map(User::getName).collect(Collectors.toList());
        System.out.println(strList);



        /*
        * 手写Lambda
        */
        AuroraList<User> auroraList = new AuroraList<User>();
        auroraList.add(new User(1001L,"张三", 18));
        auroraList.add(new User(1002L,"李四", 19));
        auroraList.add(new User(1003L,"王五", 20));

        AuroraList<String> aStrList = auroraList.filter(f -> f.getAge() > 18).map(User::getName);
        System.out.println(aStrList);
    }

}