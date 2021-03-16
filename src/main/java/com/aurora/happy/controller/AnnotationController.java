package com.aurora.happy.controller;

import com.aurora.happy.annotation.Flower;

/**
 * Created by pijiang on 2021/3/16.
 */
@Flower(name = "abc")
public class AnnotationController {

    @Flower(name = "mg", subName = {"hmg","bmg"})
    private void SayBye(){
        System.out.println("春天来了，花就开了。");
    }
}