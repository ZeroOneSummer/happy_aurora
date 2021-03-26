package com.aurora.happy.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pijiang on 2021/3/26.
 * ThreadLocal工具类，存储全局变量
 */
public class ThreadLocalUtil {
    private static final ThreadLocal<Map<String, Object>> THREAD_CONTEXT = new ThreadLocal<>();

    public static void put(String key, Object value){
        Map<String, Object> map = THREAD_CONTEXT.get();
        if (map == null) {
            map = new HashMap<>();
            THREAD_CONTEXT.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key){
        Map<String, Object> map = THREAD_CONTEXT.get();
        return map == null ? null : map.get(key);
    }

    public static void remove(String key){
        Map<String, Object> map = THREAD_CONTEXT.get();
        if(map != null) map.remove(key);
    }

    public static void clear(){
        THREAD_CONTEXT.remove();
    }
}