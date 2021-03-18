package com.aurora.happy.test;

class Father{
    Father(){
        System.out.println("Father: "+this.getClass().getName());
    }
}

class Son extends Father {
    Son(){
        System.out.println("Son: "+this.getClass().getName());
    }
}

class Daughter extends Father {
    Daughter(){
        System.out.println("Daughter: "+this.getClass().getName());
    }
}

/**
 * Created by pijiang on 2021/3/15.
 * 父类this -> 子类this
 */
public class ThisTest {
    public static void main(String[] args) {
        /**
         * new一个子类对象
         * com.aurora.happy.test.Son
         * com.aurora.happy.test.Daughter
         */
        Son son = new Son();
        Daughter daughter = new Daughter();
    }
}