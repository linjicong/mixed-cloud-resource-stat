package com.linjicong.cloud.stat.util;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal工具类
 * 用于在线程本地存储中保存和获取数据
 * 主要用于在MyBatis拦截器中传递扩展信息（如配置名称、云厂商、区域等）
 * 
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
public class ThreadLocalUtil {

    private ThreadLocalUtil() {
    }

    private final static ThreadLocal<Map<String, Object>> THREAD_CONTEXT = ThreadLocal.withInitial(()->new HashMap<>(8));

    public static Object get(String key) {
        return getContextMap().get(key);
    }

    public static void put(String key, Object value) {
        getContextMap().put(key, value);
    }

    public static Object remove(String key) {
        return getContextMap().remove(key);
    }

    public static void remove() {
        getContextMap().clear();
    }

    public static void clear() {
        THREAD_CONTEXT.remove();
    }

    private static Map<String, Object> getContextMap() {
        return THREAD_CONTEXT.get();
    }
}
