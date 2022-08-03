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
package com.linjicong.cloud.stat.service;

import cn.hutool.extra.spring.SpringUtil;
import com.linjicong.cloud.stat.dao.constant.CloudConstant;
import com.linjicong.cloud.stat.dao.entity.CloudConf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class CloudFactory {

    public static final Map<Integer, CloudService> cachedServiceMap = new HashMap<>();

    public static CloudService getService(CloudConf cloudConf) {
        switch (cloudConf.getProvider()) {
            case CloudConstant.H_CLOUD:
                return SpringUtil.getBean("HCloudService");
            case CloudConstant.Q_CLOUD:
                return SpringUtil.getBean("QCloudService");
            default:
                throw new RuntimeException("供应商配置错误");
        }
    }
}
