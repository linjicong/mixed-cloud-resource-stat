/*
 * MIT License
 *
 * Copyright (c) 2022 linjicong
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.linjicong.cloud.stat.util;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置文件工具
 * @author linjicong
 * @version 1.0.0
 * @date 2022-09-14-17:19
 */
public class YamlPropertyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(YamlPropertyUtil.class);

    private static Map<String, String> properties = new HashMap<>();

    static {
        loadProps();
    }

    private YamlPropertyUtil() {
    }

    private static void loadProps() {
        Yaml yaml = new Yaml();
        try (InputStream in = YamlPropertyUtil.class.getClassLoader().getResourceAsStream("application.yaml")) {
            Map<String,Object> map = yaml.loadAs(in, Map.class);
            for(Map.Entry<String,Object> entry : map.entrySet()){
                if(entry.getValue() instanceof Map){
                    eachYaml(entry.getKey(),(Map<String,Object>)entry.getValue());
                }else{
                    map.put(entry.getKey(),entry.getValue().toString());
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 使用递归进行深度转换,将Map<String,Object>转换为Map<String,String>;
     * @param key 父级key
     * @param map 父级entry
     */
    private static void eachYaml(String key,Map<String,Object> map){
        for (Map.Entry<String,Object> entry : map.entrySet()){
            String newKey = "";
            if(StrUtil.isNotEmpty(key)){
                newKey = (key + "." + entry.getKey());
            }else{
                newKey = entry.getKey();
            }
            if(entry.getValue() instanceof Map){
                eachYaml(newKey,(Map<String,Object>)entry.getValue());
            }else{
                properties.put(newKey,entry.getValue().toString());
            }
        }
    }

    /**
     *  根据key 获取指定的值(默认文件)
     */
    public static String getProperty(String key){
        return properties.get(key);
    }

    public static void main(String[] args) {
        loadProps();
    }
}
