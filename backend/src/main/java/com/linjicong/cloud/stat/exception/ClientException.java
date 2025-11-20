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
package com.linjicong.cloud.stat.exception;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 云客户端异常类
 * 用于封装云API调用过程中产生的异常
 * 
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-05
 */
public class ClientException extends RuntimeException{
    private static final long serialVersionUID = 8247610127853014183L;

    /**
     * 通过异常对象构造
     * 
     * @param e 异常对象
     */
    public ClientException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    /**
     * 通过异常消息构造
     * 
     * @param message 异常消息
     */
    public ClientException(String message) {
        super(message);
    }

    /**
     * 通过消息模板和参数构造
     * 
     * @param messageTemplate 消息模板
     * @param params 参数
     */
    public ClientException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    /**
     * 通过异常消息和异常对象构造
     * 
     * @param message 异常消息
     * @param throwable 异常对象
     */
    public ClientException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * 完整构造方法
     * 
     * @param message 异常消息
     * @param throwable 异常对象
     * @param enableSuppression 是否启用抑制
     * @param writableStackTrace 是否可写堆栈跟踪
     */
    public ClientException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    /**
     * 通过异常对象、消息模板和参数构造
     * 
     * @param throwable 异常对象
     * @param messageTemplate 消息模板
     * @param params 参数
     */
    public ClientException(Throwable throwable, String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params), throwable);
    }
}
