package com.aurora.happy.test;

import com.alibaba.fastjson.JSON;
import com.aurora.happy.pojo.Creature;
import com.aurora.happy.pojo.Human;
import com.aurora.happy.pojo.Human.Chinese;
import com.aurora.happy.pojo.Human.Japanese;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pijiang on 2021/3/18.
 * 泛型
 */
public class GenericTest {

    public static void main(String[] args) {

        List<Creature> creatList = new ArrayList<>();
        List<Human> humList = new ArrayList<>();
        List<Chinese> chnList = Arrays.asList(new Chinese("李健"), new Chinese("周深"));
        List<Japanese> japList = Arrays.asList(new Japanese("苍井优"), new Japanese("堀北真希"));

        /**
         * 【上边界 ? extends T】
         *  指向：<=自己，List<Human>、List<Chinese>、List<Japanese>
         *  存：X
         *  取：Human
         *  PS：返回值类型精度高，适合查询
         */
        List<? extends Human> humanList = humList;
        humanList = chnList;
        humanList = japList;
//        humanList.add(new Japanese("路飞")));
        Human human = humanList.get(0);
        System.out.println(JSON.toJSONString(human));


        /**
         * 【下边界 ? super T】
         *  指向：>=自己，List<Human>、List<Creature>
         *  存：<=自己
         *  取：Object
         *  转：[子，父]
         *  PS：返回值类型模糊，适合插入
         */
        List<? super Human> creList = humList;
        creList = creatList;
//        creList = japList;
        creList.add(new Chinese("刘德华"));
        creList.add(new Japanese("三浦春马"));
        creList.add(new Human("人类"));
        System.out.println(JSON.toJSONString(creatList));
        Object object = creList.get(0);
        System.out.println(JSON.toJSONString(object));
        System.out.println(((Creature)object).getName());
        System.out.println(((Human)object).getName());
        System.out.println(((Chinese)object).getName());

        /**
         * 【无边界 ?】
         *  指向：List<Object>
         *  存：X
         *  取：Object
         *  转：Everything
         *  PS：由于可以转任何类型，容易出现 CLASS CAST EXCEIPTION!
         */
        List<?> list = chnList;
        list = humList;
        list = creList;
        Object obj = list.get(0);
        System.out.println(((Creature)obj).getName());
        System.out.println(((Human)obj).getName());
        System.out.println(((Chinese)obj).getName());
//        list = Arrays.asList(1,2,3);
//        System.out.println((Integer)obj);

        /**
         * 【无泛型】
         *  指向：List<Object>
         *  存：Everything
         *  取：Object
         *  转：Everything
         *  PS：由于可以转任何类型，容易出现 CLASS CAST EXCEIPTION!
         */
        List list2 = new ArrayList();
        list2 = humList;
        list2 = creList;
        list2 = japList;
        list2.add(1);
        list2.add("2");
        list2.add(true);
        Object o = list2.get(0);
        System.out.println(list2);
    }}