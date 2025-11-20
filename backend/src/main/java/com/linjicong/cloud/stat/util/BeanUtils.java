package com.linjicong.cloud.stat.util;

import cn.hutool.core.collection.ListUtil;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Bean工具类
 * 使用CGLib的BeanCopier实现高性能的Bean属性复制
 * 通过缓存BeanCopier对象提升性能
 * 
 * @author linjicong
 * @version 1.0.0
 */
public class BeanUtils {


    /**
     * 缓存BeanCopier 对象 提升性能
     */
    private static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>();


    /**
     * Bean属性复制工具方法。
     * @param sources   原始集合
     * @param supplier: 目标类::new(eg: UserVO::new)
     */
    public static <S, T> List<T> cgLibCopyList(List<S> sources, Supplier<T> supplier) {
        List<T> list = new ArrayList<>(sources.size());
        BeanCopier beanCopier = null;
        for (S source : sources) {
            T t = supplier.get();
            if (beanCopier == null) {
                beanCopier = getBeanCopier(source.getClass(), t.getClass());
            }
            beanCopier.copy(source, t, null);
            list.add(t);
        }
        return list;
    }

    /**
     * Bean属性复制工具方法。
     * @param sources   原始集合
     * @param supplier: 目标类::new(eg: UserVO::new)
     */
    public static <S, T> List<T> cgLibCopyList(S[] sources, Supplier<T> supplier) {
        ArrayList<S> sourceList = ListUtil.toList(sources);
        return cgLibCopyList(sourceList, supplier);
    }

    /**
     * Bean属性复制工具方法。
     * @param source    目标对象
     * @param supplier: 目标类::new(eg: UserVO::new)
     */
    public static <T> T cgLibCopyBean(Object source, Supplier<T> supplier) {
        T t = supplier.get();
        getBeanCopier(source.getClass(), t.getClass()).copy(source, t, null);
        return t;
    }


    //获取BeanCopier对象 如果缓存中有从缓存中获取 如果没有则新创建对象并加入缓存
    private static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> targetClass) {
        String key = getKey(sourceClass.getName(), targetClass.getName());
        BeanCopier beanCopier;
        beanCopier = BEAN_COPIER_MAP.get(key);
        if (beanCopier == null) {
            beanCopier = BeanCopier.create(sourceClass, targetClass, false);
            BEAN_COPIER_MAP.put(key, beanCopier);
        }
        return beanCopier;
    }

    /**
     * 生成缓存key
     */
    private static String getKey(String sourceClassName, String targetClassName) {
        return sourceClassName + targetClassName;
    }


}

