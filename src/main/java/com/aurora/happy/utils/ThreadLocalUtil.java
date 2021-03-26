package com.aurora.happy.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pijiang on 2021/3/26.
 * ThreadLocal工具类，存储全局变量
 */
public class ThreadLocalUtil {
    private static final ThreadLocal<Map<String, Object>> THREAD_CONTEXT = new initThreadLocal();

    public static void put(String key, Object value){
        getThreadLocal().put(key, value);
    }

    public static Object get(String key){
        return getThreadLocal().get(key);
    }

    public static void remove(String key){
        getThreadLocal().remove(key);
    }

    public static void clear(){
        THREAD_CONTEXT.remove();
    }

    /**
     * 获取ThreadLocal
     * @return
     */
    private static Map<String, Object> getThreadLocal(){
        return THREAD_CONTEXT.get();
    }

    /**
     *  THREAD_CONTEXT.get()会调用ThreadLocal.initialValue()
     * 初始化ThreadLocalMap，减少非null判断
     */
    private static class initThreadLocal extends ThreadLocal<Map<String, Object>> {
        @Override
        protected Map<String, Object> initialValue() {
            //初始化map
            return new HashMap<String, Object>(8){
                private static final long serialVersionUID = 3637958959138295593L;
                @Override
                public Object put(String key, Object value) {
                    return super.put(key, value);
                }
            };
        }
    }
}