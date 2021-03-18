package com.aurora.happy.test;

import com.aurora.happy.enums.Friut;
import com.aurora.happy.utils.EnumUtil;

import java.util.Optional;

/**
 * Created by pijiang on 2021/3/18.
 */
public class EnumTest {
    public static void main(String[] args) {
        Optional<Friut> optioh = EnumUtil.getEnumWithCache(Friut.class, friut -> friut.name(), Friut.APPLE);
        System.out.println(optioh.get());
    }
}