package com.linjicong.cloud.stat.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 反射工具
 *
 * @author linjicong
 * @date 2019/4/26 18:26
 */

public class ReflectAsmUtil {
    private ReflectAsmUtil() {
    }

    private static final Map<Class<?>, MethodAccess> methodMap = new HashMap<>();

    private static final Map<Class<?>, List<String>> fieldMap = new HashMap<>();

    private static final Map<Class<?>, Map<String, String>> fieldTypeMap = new HashMap<>();

    private static final Map<Class<?>, Map<String, Integer>> methodIndexOfGetMap = new HashMap<>();

    private static final Map<Class<?>, Map<String, Integer>> methodIndexOfSetMap = new HashMap<>();

    /**
     * 拷贝对象
     */
    public static void copyProperties(Object source, Object target) {
        MethodAccess sourceMethodAccess = methodMap.get(source.getClass());
        if (sourceMethodAccess == null) {
            sourceMethodAccess = cache(source);
        }
        MethodAccess targetMethodAccess = methodMap.get(target.getClass());
        if (targetMethodAccess == null) {
            targetMethodAccess = cache(target);
        }
        Map<String, Integer> sourceMethodIndexOfGet = methodIndexOfGetMap.get(source.getClass());
        Map<String, Integer> targetMethodIndexOfSet = methodIndexOfSetMap.get(target.getClass());

        //setDefaultValue(target);

        List<String> fieldList = fieldMap.get(source.getClass());
        for (String field : fieldList) {
            String fieldName = StrUtil.upperFirst(field);
            Integer setIndex = targetMethodIndexOfSet.get(fieldName);
            if (setIndex != null) {
                int getIndex = sourceMethodIndexOfGet.get(fieldName);
                // 参数一需要反射的对象
                // 参数二class.getDeclaredMethods 对应方法的index
                // 参数对三象集合
                targetMethodAccess.invoke(target, setIndex, sourceMethodAccess.invoke(source, getIndex));
            }
        }
    }

    /**
     * 拷贝对象
     */
    public static <T, O> List<T> copyProperties(List<O> sourceList, Class<T> clazz) {
        List<T> targetList = new ArrayList<>();
        for (Object source : sourceList) {
            T target;
            try {
                target = clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException();
            }
            copyProperties(source, target);
            targetList.add(target);
        }
        return targetList;
    }

    /**
     * 获取对象属性值
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        MethodAccess objMethodAccess = methodMap.get(obj.getClass());
        if (objMethodAccess == null) {
            objMethodAccess = cache(obj);
        }
        Map<String, Integer> objMethodIndexOfGetMap = methodIndexOfGetMap.get(obj.getClass());
        Integer getIndex = objMethodIndexOfGetMap.get(StrUtil.upperFirst(fieldName));
        if (getIndex != null) {
            return objMethodAccess.invoke(obj, getIndex);
        }
        return null;
    }

    /**
     * 设置对象属性值
     */
    public static Object setFieldValue(Object obj, String fieldName, Object fieldValue) {
        MethodAccess objMethodAccess = methodMap.get(obj.getClass());
        if (objMethodAccess == null) {
            objMethodAccess = cache(obj);
        }
        Map<String, Integer> objMethodIndexOfSetMap = methodIndexOfSetMap.get(obj.getClass());
        Integer setIndex = objMethodIndexOfSetMap.get(StrUtil.upperFirst(fieldName));
        if (setIndex != null) {
            return objMethodAccess.invoke(obj, setIndex,fieldValue);
        }
        return null;
    }

    /**
     * 设置默认值,比普通set方法慢10倍
     */
    public static void setDefaultValue(Object obj) {
        MethodAccess objMethodAccess = methodMap.get(obj.getClass());
        if (objMethodAccess == null) {
            objMethodAccess = cache(obj);
        }
        Map<String, String> fieldMap = fieldTypeMap.get(obj.getClass());
        Map<String, Integer> objMethodIndexOfSetMap = methodIndexOfSetMap.get(obj.getClass());
        Map<String, Integer> objMethodIndexOfGetMap = methodIndexOfGetMap.get(obj.getClass());
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            String fieldName = StrUtil.upperFirst(entry.getKey());
            int getIndex = objMethodIndexOfGetMap.get(fieldName);
            Object fieldValue = objMethodAccess.invoke(obj, getIndex);
            if(fieldValue == null) {
                int setIndex = objMethodIndexOfSetMap.get(fieldName);
                String fieldType = entry.getValue();
                switch (fieldType) {
                    case "String":
                        objMethodAccess.invoke(obj, setIndex, "");
                        break;
                    case "Integer":
                    case "int":
                        objMethodAccess.invoke(obj, setIndex, 0);
                        break;
                    case "Long":
                    case "long":
                        objMethodAccess.invoke(obj, setIndex, 0L);
                        break;
                    case "Double":
                    case "double":
                        objMethodAccess.invoke(obj, setIndex, 0.0);
                        break;
                    case "BigDecimal":
                        objMethodAccess.invoke(obj, setIndex, BigDecimal.ZERO);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 单例模式
     */
    private static MethodAccess cache(Object source) {
        MethodAccess methodAccess = MethodAccess.get(source.getClass());
        Field[] fields = source.getClass().getDeclaredFields();
        Field[] superFields = source.getClass().getSuperclass().getDeclaredFields();
        Field[] fieldsFinal = ArrayUtil.addAll(fields, superFields);
        List<String> fieldList = new ArrayList<>(fieldsFinal.length);
        Map<String, String> fieldsTypeMap = new HashMap<>(fieldsFinal.length);
        Map<String, Integer> indexOfGetMap = new HashMap<>(fieldsFinal.length);
        Map<String, Integer> indexOfSetMap = new HashMap<>(fieldsFinal.length);

        for (Field field : fieldsFinal) {
            // 是否是私有的，是否是静态的
            if (Modifier.isPrivate(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
                // 非公共私有变量
                // 获取属性名称
                String fieldName = StrUtil.upperFirst(field.getName());
                // 获取get方法的下标
                int getIndex = methodAccess.getIndex("get" + fieldName);
                indexOfGetMap.put(fieldName, getIndex);
                // 获取set方法的下标
                int setIndex = methodAccess.getIndex("set" + fieldName);
                indexOfSetMap.put(fieldName, setIndex);
                // 将属性名称放入集合里
                fieldList.add(fieldName);
                fieldsTypeMap.put(fieldName, field.getType().getSimpleName());
            }
        }
        // 将类名，属性名称注册到map中
        fieldMap.put(source.getClass(), fieldList);
        fieldTypeMap.put(source.getClass(), fieldsTypeMap);
        methodIndexOfGetMap.put(source.getClass(), indexOfGetMap);
        methodIndexOfSetMap.put(source.getClass(), indexOfSetMap);
        // 将类名，方法名称注册到map中
        methodMap.put(source.getClass(), methodAccess);
        return methodAccess;
    }

    public static List<String> getFieldMap(Object obj){
        MethodAccess sourceMethodAccess = methodMap.get(obj.getClass());
        if (sourceMethodAccess == null) {
            sourceMethodAccess = cache(obj);
        }
        return fieldMap.get(obj.getClass());
    }
}
