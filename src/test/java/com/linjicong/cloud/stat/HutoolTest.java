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
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.linjicong.cloud.stat.dao.entity.hcloud.HCloudEcs;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
        String content = "test??????";

        // ??????????????????
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();

        // ??????
        AES aes = SecureUtil.aes(key);

        // ??????
        byte[] encrypt = aes.encrypt(content);
        // ??????
        byte[] decrypt = aes.decrypt(encrypt);

        // ?????????16????????????
        String encryptHex = aes.encryptHex(content);
        System.out.println(encryptHex);
        // ??????????????????
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println(decryptStr);
    }

    @Test
    void testList(){
        List<HCloudEcs> list = new ArrayList<HCloudEcs>();
        Type genericSuperclass = list.getClass().getGenericSuperclass();
        System.out.println(((ParameterizedType) list.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }
}
