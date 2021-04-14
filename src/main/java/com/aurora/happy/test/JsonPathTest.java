package com.aurora.happy.test;

import com.alibaba.fastjson.JSON;
import com.aurora.happy.pojo.User;
import com.jayway.jsonpath.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by pijiang on 2021/4/13.
 * JsonPath工具
 */
public class JsonPathTest {

    private static final String jsonStr =  "{\"store\": {\"book\": [{\"author\": \"Li\", \"price\": 20.00}, {\"author\": \"Chen\", \"price\": 25.00}] }, \"version\": 1.0}";

    /**
     * 常规解析
     */
    private static void test_1(){
        String f1 = "$";                        //{store={book=[{"author":"Li","price":20.0},{"author":"Chen","price":25.0}]}, version=1.0}
        String f2 = "$.store.book[0].author";   //Li
        String f3 = "$.store.book[*].author";   //["Li","Chen"]
        String f4 = "$..author";                //["Li","Chen"]
        String f5 = "$.store..author";          //["Li","Chen"]
        String f6 = "$..book[?(@.price)]";      //[{"author":"Li","price":20.0},{"author":"Chen","price":25.0}]
        String f7 = "$..book[?(@.price > 20)]"; //[{"author":"Chen","price":25.0}]
        String f8 = "$..book.length()";         //[2]
        String f9 = "$..book[?(@.author =~ /.*en/i)]";                          //正则匹配，[{"author":"Chen","price":25.0}]
        String f10 = "$..book[?(@['author'] == 'Chen' && @['price'] <= 30)]";   //[{"author":"Chen","price":25.0}]

        List<String> strings = Arrays.asList(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10);
        for (String jsonPath : strings) {
            Object read = JsonPath.read(jsonStr, jsonPath);
            System.out.println(read);
        }
    }

    /**
     * 对象解析
     */
    private static void test_2(){
        User user = new User("linda", 20);
        //便于复用
        DocumentContext dc = JsonPath.parse(JSON.toJSON(user));
        System.out.println(dc.read("$.name").toString());
        System.out.println(dc.read("$.age").toString());
    }

    /**
     * 日期解析
     */
    private static void test_3(){
        String jsonStr = "{\"createDate\" : 1618388652982}";
        Date date = JsonPath.parse(jsonStr).read("$['createDate']", Date.class);
        Date date2 = JsonPath.parse(jsonStr).read("$.createDate", Date.class);
        System.out.println(date);
        System.out.println(date2);
    }

    /**
     * 过滤器解析
     */
    private static void test_4(){
        //等价于[?(@['author'] == 'Chen' && @['price'] <= 30)]
        Filter filter = Filter.filter(Criteria.where("author").is("Chen").and("price").lte(30D));
        List<Map<String, Object>> read = JsonPath.parse(jsonStr).read("$.store.book[?]", filter);
        System.out.println(read);   //[{"author":"Chen","price":25.0}]

        //Option
        Configuration configuration = Configuration.builder().options(Option.AS_PATH_LIST).build(); //["$['store']['book'][0]['author']","$['store']['book'][1]['author']"]
        Configuration configuration2 = Configuration.defaultConfiguration();    //["Li","Chen"]
        List<String> pathList = JsonPath.using(configuration2).parse(jsonStr).read("$..author");
        System.out.println(pathList);
    }


    public static void main(String[] args) {
//        test_1();
//        test_2();
//        test_3();
        test_4();
    }
}