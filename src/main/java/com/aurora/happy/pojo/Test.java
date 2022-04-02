package com.aurora.happy.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pijiang on 2021/5/21.
 */
@Data
@AllArgsConstructor
class UserInfo implements Serializable {
    private int id;
    private Address address;
}
@Data
@AllArgsConstructor
class Address implements Serializable {
    private String country;
    private String province;
    private List<City> cities;
}
@Data
@AllArgsConstructor
class City implements Serializable {
    private String city;
}


@Data
@AllArgsConstructor
@NoArgsConstructor
class UserInfo2 implements Serializable {
    private int id;
    private Address2 address;
}
@Data
@AllArgsConstructor
@NoArgsConstructor
class Address2 implements Serializable {
    private String country;
    private String province;
    private List<String> cities;
}


public class Test {

    public static void main(String[] args) throws Exception{
        List<City> cities = new ArrayList<City>(){{
            add(new City("东莞市"));
            add(new City("东莞市"));
        }};
        Address address = new Address("中国", "广东省", new ArrayList<>());
        UserInfo userInfo = new UserInfo(1001, address);
        String strJson = new ObjectMapper().writeValueAsString(userInfo);
        System.out.println(strJson);


        UserInfo2 userInfo2 = new ObjectMapper().readValue(strJson, UserInfo2.class);
        Object citys = userInfo2.getAddress().getCities();
        System.out.println(citys);
    }
}