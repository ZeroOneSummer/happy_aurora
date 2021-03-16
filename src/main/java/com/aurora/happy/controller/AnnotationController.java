package com.aurora.happy.controller;

import com.aurora.happy.annotation.Flower;
import com.aurora.happy.contanst.Contants;

/**
 * Created by pijiang on 2021/3/16.
 */
@Flower(name = Contants.RED)
public class AnnotationController {

    @Flower(name = Contants.BLUE, subName = {"hmg","bmg"})
    private void SayBye(){
        System.out.println("春天来了，花就开了。");
    }
}