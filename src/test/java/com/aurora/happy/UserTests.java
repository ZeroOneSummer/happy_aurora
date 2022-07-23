package com.aurora.happy;

import com.aurora.happy.base.WebAppTest;
import com.aurora.happy.mapper.UserMapper;
import com.aurora.happy.pojo.User;
import lombok.SneakyThrows;
import lombok.var;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

//@RunWith(SpringRunner.class)
//@ContextConfiguration("classpath:applicationContext.xml") //指定启动文件
//@Profile("dev") //指定启动环境
//@WebAppConfiguration //默认指定webapp
public class UserTests extends WebAppTest {

    @Autowired
    UserMapper userMapper;

    /**
     * 1.AbstractTransactionalJUnit4SpringContextTests 含有 @Transactional，不要随意继承
     * 2.Junit4中使用 @Transactional 默认 @Rollback(true) ，不管执行成功与否全部回滚
     * 3.使用时需组合使用 @Transactional + @Rollback(false) / @Commit，可以关闭回滚
     * 4.如想正常使用事务，须在测试层加 @Test @Transactional @Rollback(false) ，在服务层加 @Transactional
     *   并且异常只能从服务层抛出才能生效，而不能在测试层发生
     */
    @Transactional
    @Rollback(false)
    @Commit
    @SneakyThrows
    @Test
    public void addUser(){
        var user = User.builder()
                .id(1001L)
                .age(18)
                .name("lisa")
                .build();
        var rs = userMapper.addUser(user);
        assertEquals("添加用户失败！", rs, 1);
    }
}
