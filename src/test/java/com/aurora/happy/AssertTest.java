package com.aurora.happy;

import com.aurora.happy.base.WebAppTest;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pijiang on 2021/4/16.
 * 关键字assert的使用，设置虚拟机参数VM options：-ea
 * Junit断言
 *
 * import static org.junit.Assert.*;
 * import static org.hamcrest.MatcherAssert.*;
 * import static org.hamcrest.CoreMatchers.*;
 * import static org.hamcrest.Matchers.*;
 */
@Slf4j
public class AssertTest extends WebAppTest {

    @Test
    public void testAssert() {
        Assert.fail("异常信息抛出");
        Assert.assertTrue(StringUtils.hasText("a"));
        Assert.assertFalse("不能有值", StringUtils.hasText("a"));
        Assert.assertEquals("obj1和obj2的值不相同", new HashMap<String, String>(){{put("k", "v");}}, new HashMap<>());	//比较值
        Assert.assertSame("obj1和obj2不是同一个对象", new HashMap<>(), new HashMap<>());	    //比较引用
        Assert.assertEquals("超出符合误差0.02", 0.55, 0.58, 0.02);	//异常信息、期望值、实际值、允许误差
        Assert.assertNotNull("obj不能为null", null);
    }

    @Test
    public void testAssert2() {
        Assert.assertThat("http://www.taobao.com", CoreMatchers.containsString("taobao"));
        Assert.assertThat("http://www.taobao.com", CoreMatchers.endsWith(".com"));
        Assert.assertThat("http://www.taobao.com", CoreMatchers.equalTo(".com"));
        Assert.assertThat("http://www.taobao.com", Matchers.equalToIgnoringCase("HTTP://WWW.TAOBAO.COM"));
        Assert.assertThat(156, Matchers.lessThanOrEqualTo(100));  // <=
        Assert.assertThat(100, Matchers.is(100));   // ==
        Assert.assertThat(156, CoreMatchers.allOf(Matchers.greaterThan(0), Matchers.lessThan(100)));  // and
        Assert.assertThat(110, CoreMatchers.anyOf(Matchers.greaterThan(0), Matchers.lessThan(100)));  // or
        Assert.assertThat(110, CoreMatchers.not(100));  // not
    }

    @Test
    public void testAssert3() {
        List<String> list = Arrays.asList("1", "2", "3");
        Map<String, String> map = new HashMap<String, String>(){{put("k1", "1");}};

        Assert.assertThat(list, CoreMatchers.hasItem("2"));     //存在该元素
        Assert.assertThat(map, Matchers.hasKey("k2"));          //存在该元素
    }
}