package com.aurora.happy.pojo;

/**
 * Created by pijiang on 2021/3/18.
 */
public class Human extends Creature{

    public Human(String name) {
        super(name);
    }

    public static class Chinese extends Human {
        public Chinese(String name) {
            super(name);
        }
    }

    public static class Japanese extends Human {
        public Japanese(String name) {
            super(name);
        }
    }
}


