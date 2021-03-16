package com.aurora.happy.test;

import com.aurora.happy.pojo.Couple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pijiang on 2021/3/15.
 */
public class CoupleTest {

    public static void main(String[] args) {
        // 用于计算循环次数
        int count = 0;

        // 老公组
        List<Couple> husbands = new ArrayList<>();
        husbands.add(new Couple(1, "梁山伯"));
        husbands.add(new Couple(2, "牛郎"));
        husbands.add(new Couple(3, "干将"));
        husbands.add(new Couple(4, "工藤新一"));
        husbands.add(new Couple(5, "罗密欧"));

        // 老婆组
        List<Couple> wives = new ArrayList<>();
        wives.add(new Couple(1, "祝英台"));
        wives.add(new Couple(2, "织女"));
        wives.add(new Couple(3, "莫邪"));
        wives.add(new Couple(4, "毛利兰"));
        wives.add(new Couple(5, "朱丽叶"));


        String collect = husbands.stream()
                .flatMap(husbands1 -> wives.stream().filter(wives1 -> husbands1.getFamilyId().equals(wives1.getFamilyId()))
                .map(wives1 -> husbands1.getUserName() + "爱" + wives1.getUserName()))
                .collect(Collectors.joining("\r\n"));

        System.out.println(collect);
    }
}