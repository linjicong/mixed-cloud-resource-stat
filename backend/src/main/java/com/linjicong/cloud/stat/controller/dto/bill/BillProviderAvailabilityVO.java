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
package com.linjicong.cloud.stat.controller.dto.bill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 厂商账单可用性 VO（阿里云占位用）。
 *
 * <p>{@code available=false} 表示该厂商在 {@code cloud_conf} 里有账号，但账单数据尚未采集，
 * 前端据此渲染「该厂商账单数据暂未采集」占位，未来阿里云接入后前端无需改动。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillProviderAvailabilityVO {

    /** 厂商标识：huawei / tencent / aliyun */
    private String provider;

    /** 厂商中文名 */
    private String label;

    /** 该厂商账单数据是否可用 */
    private Boolean available;

    /** 该厂商在筛选范围内的账号数 */
    private Integer accountCount;

    /** 不可用时的提示文案，可用时为 null */
    private String message;
}
