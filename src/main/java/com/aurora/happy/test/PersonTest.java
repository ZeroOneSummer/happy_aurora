package com.aurora.happy.test;

import com.aurora.happy.utils.ConvertUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pijiang on 2021/3/15.
 */
public class PersonTest {

    private static List<Person> list;

    static {
        list = new ArrayList<>();
        list.add(new Person("i", 18, "杭州", 999.9));
        list.add(new Person("am", 19, "温州", 777.7));
        list.add(new Person("iron", 21, "杭州", 888.8));
        list.add(new Person("man", 17, "宁波", 888.8));
    }


    public static void main(String[] args) {
        Map<String, Person> nameToPersonMap = ConvertUtil.listToMap(list, Person::getName);
        System.out.println(nameToPersonMap);

        Map<String, Person> personGt18 = ConvertUtil.listToMap(list, Person::getName, person -> person.getAge() >= 18);
        System.out.println(personGt18);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Person {
        private String name;
        private Integer age;
        private String address;
        private Double salary;
    }
}