package com.aurora.happy;

import com.aurora.happy.base.WebAppTest;
import com.aurora.happy.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Stubber;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by pijiang on 2021/4/12.
 * Mockito介绍：有些对象需要依赖某些环境，Mockito可以模拟任何对象，不关注依赖链，对行为、结果...进行模拟
 */
@Slf4j
//@TestMethodOrder(MethodOrderer.Alphanumeric.class)  //按方法字母顺序测试
public class MockTest extends WebAppTest {

    /**
     * 模拟对象
     */
    @Test
    @Order(1)
    public void testMock() {
        //1.创建mock对象
        List mockList = mock(List.class);

        //2.使用mock对象，后续调用动作都会存mock中
        mockList.add("1");
        mockList.clear();

        //3.行为验证
        verify(mockList).add("1");      //有调用，通过
//        verify(mockList).add("2");      //没调用过，异常
        verify(mockList).clear();       //有调用，通过
        verify(mockList, times(1)).add("1");     //验证调用次数，1-OK，2-异常
        verify(mockList, never()).add("zos");   //验证一次也没调用，atLeast(2)/atMost(5) 最少2次/最多5次

        //4.设置调用的预期值
        when(mockList.get(0)).thenReturn("给get(0)设置的值");
        log.info(mockList.get(0).toString());   //result: 给get(0)设置的值

        when(mockList.get(anyInt())).thenReturn("get(anyInt)，随机的结果");
        log.info(mockList.get(0).toString());   //result: get(anyInt)，随机的结果
        log.info(mockList.get(999).toString()); //result: get(anyInt)，随机的结果

        Stubber stubber = doReturn("看你可怜，给你一个结果吧");
        stubber.when(mockList).get(333);
        log.info(mockList.get(333).toString()); //看你可怜，给你一个结果吧

            //链式设置预期
        when(mockList.get(0)).thenReturn("取出一个").thenThrow(new RuntimeException("没有了哦！"));
        log.info(mockList.get(0).toString());   //result: 看你可怜，给你一个结果吧
        log.info(mockList.get(0).toString());   //result: java.lang.RuntimeException: 没有了哦！

        doThrow(new RuntimeException("别乱get，给你一个异常，自己体会！")).when(mockList).get(1);
        mockList.get(1);                        //result: java.lang.RuntimeException: 别乱get，给你一个异常，自己体会！

        when(mockList.get(1)).thenThrow(new RuntimeException("get(1)没值哒，不要调了！"));
        log.info(mockList.get(1).toString());   //result: java.lang.RuntimeException: get(1)没值嗒，不要调了！
    }

    /**
     * 模拟真实对象
     */
    @Test
    @Order(2)
    public void testMock2() {
        List linkedList = new LinkedList();
        List realList = spy(linkedList);
        //打桩：模拟返回
        when(realList.size()).thenReturn(100);
        log.info("list rows: {}", realList.size());

        //使用对象的真实方法
        realList.add("1");
        log.info("get value: {}", realList.get(0));  //mock(linkedList)不会真正add值

        //交互验证
        verify(realList).add("1");
    }

    /**
     * 模拟Service
     */
    @Test
    @Order(3)
    public void testGetOrder() {
        //when()不能是void方法，模拟调用方法时，返回设置的结果
        OrderService orderMock = mock(OrderService.class);
        when(orderMock.getOrders()).thenReturn(Arrays.asList("1001","1002","1003"));
        //模拟调用
        List<String> orders = orderMock.getOrders();
        log.info(orders.toString());
    }

    /**
     * 部分真实调用
     */
    @Test
    @Order(4)
    public void testAddOrder() {
        OrderService orderMock = mock(OrderService.class);
        orderMock.addOrder("Jack Chen");
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(orderMock).addOrder(argument.capture());    //捕捉参数
        log.info("参数：{}", argument.getValue());
        assertEquals("BluceLee", argument.getValue());  //断言

        when(orderMock.addOrder("Linda")).thenCallRealMethod();  //返回真实方法
        int rows = orderMock.addOrder("Linda");
        log.info("row: {}", rows);
    }
}