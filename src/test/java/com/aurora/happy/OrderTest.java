package com.aurora.happy;

import com.alibaba.fastjson.JSON;
import com.aurora.happy.base.WebAppTest;
import com.aurora.happy.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by pijiang on 2021/4/12.
 * Web测试：https://blog.csdn.net/wo541075754/article/details/88983708
 */
@Slf4j
public class OrderTest extends WebAppTest {

    private MockMvc mockMvc;

    @Autowired
    public void setMockMvc(WebApplicationContext webApplicationContext){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

//    @Before
//    public void setUp(){
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }

    @Test
    public void testGetWeb() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/getOrders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userName","cai")             //参数
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())    //期望值
                                .andDo(MockMvcResultHandlers.print())
                                .andReturn().getResponse().getContentAsString();     //响应
        log.info("response: {}", result);
    }

    @Test
    public void testPostWeb() throws Exception {
        String requestBody = JSON.toJSONString(new User(1001L,"海伦", 20));
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user/getOrders2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                                .accept(MediaType.APPLICATION_JSON))    //执行请求
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))   //响应contentType
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn().getResponse().getContentAsString();
        log.info("response: {}", result);
    }
}