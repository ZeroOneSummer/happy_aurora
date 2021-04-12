package com.aurora.happy.base;

import com.aurora.happy.MockTest;
import com.aurora.happy.OrderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by pijiang on 2021/4/12.
 * 统一入口，批量测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({OrderTest.class, MockTest.class})
public class BatchTest {
}