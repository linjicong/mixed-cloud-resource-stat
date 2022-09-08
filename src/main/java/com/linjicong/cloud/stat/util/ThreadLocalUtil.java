package com.linjicong.cloud.stat.util;

import java.util.HashMap;
import java.util.Map;

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
