package com.aurora.happy.test;


import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by pijiang on 2021/4/16.
 * Spring断言
 */
public class AssertTest {

    private static void m_1(){
        Assert.hasText("", "内容不能为空!");
        Assert.isTrue(StringUtils.hasText(""), "内容不能为空!");
        Assert.state(StringUtils.hasText(""), "内容不能为空!");
        Assert.notEmpty(new String[]{}, "集合不能为空!");
        Assert.notNull(null, "不能为null!");
    }

    public static void main(String[] args) {
        m_1();
    }
}