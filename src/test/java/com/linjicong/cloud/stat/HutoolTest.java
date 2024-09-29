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
package com.linjicong.cloud.stat;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.http.HttpUtil;
import com.linjicong.cloud.stat.dao.entity.hcloud.HCloudEcs;
import com.linjicong.cloud.stat.util.SeqUUIDUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-04-15:04
 */
public class HutoolTest {
    @Test
    void dateUtil() {
        System.out.println(DateUtil.offsetDay(DateUtil.beginOfDay(new Date()), -1));
        System.out.println(DateUtil.offsetDay(DateUtil.endOfDay(new Date()), -1));
        System.out.println(DateUtil.offsetDay(DateUtil.beginOfDay(new Date()), -1).getTime());
        System.out.println(DateUtil.offsetDay(DateUtil.endOfDay(new Date()), -1).getTime());
    }

    @Test
    void aesTest(){
        String content = "test中文";

        // 随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();

        // 构建
        AES aes = SecureUtil.aes(key);

        // 加密
        byte[] encrypt = aes.encrypt(content);
        // 解密
        byte[] decrypt = aes.decrypt(encrypt);

        // 加密为16进制表示
        String encryptHex = aes.encryptHex(content);
        System.out.println(encryptHex);
        // 解密为字符串
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println(decryptStr);
    }

    @Test
    void testList(){
        List<HCloudEcs> list = new ArrayList<HCloudEcs>();
        Type genericSuperclass = list.getClass().getGenericSuperclass();
        System.out.println(((ParameterizedType) list.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Test
    void testUUID(){
        //UUID uuid = Generators.timeBasedGenerator().generate(); // Variant 1
        //UUID uuid = Generators.randomBasedGenerator().generate(); // Variant 4
        //UUID uuid = Generators.nameBasedgenerator().generate("string to hash"); // Variant 5
        //UUID uuid = Generators.timeBasedReorderedGenerator().generate(); // Variant 6
        //UUID uuid = Generators.timeBasedEpochGenerator().generate(); // Variant 7
        //System.out.println(uuid.toString());
        //System.out.println(uuid.timestamp());
    }

    //写一个test方法
    @Test
    public void test() {
        //创建一个集合
        System.out.println(SeqUUIDUtil.toSequenceUUID(DateUtil.parse("2024-09-24 09:04:27").getTime()));
    }

    @Test
    public void testCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        System.out.println(calendar.getTime());
    }
    @Test
    void test1(){
        String datetime="2023-12-05 09:03:00";
        String md5= MD5.create().digestHex(datetime+"ATJRlsLOOsLfeVsrYVNS");
        String s = HttpUtil.get("http://hradmin.zy.com/staff/listAllPartTime?datetime=" + datetime + "&md5=" + md5);
        System.out.println(s);
        //System.out.println(DateUtil.parse("2023-05-29 12:00:00").getTime());
    }
}
