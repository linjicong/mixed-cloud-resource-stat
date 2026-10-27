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
package com.linjicong.cloud.stat.service.bill.provider;

import com.linjicong.cloud.stat.service.bill.bo.BillQueryCondition;
import com.linjicong.cloud.stat.service.bill.bo.UnifiedBillRecord;

import java.util.List;

/**
 * 账单归一化 SPI（架构扩展点）。
 *
 * <p>新增厂商只需实现本接口并标注 {@code @Component}，{@code BillQueryService}
 * 通过 {@code List<BillRecordProvider>} 注入，零改动即可识别。阿里云账单采集上线后
 * 按此接口补一个实现即可。</p>
 *
 * <p>本包（{@code service.bill.provider}）与 Mapper XML 是全项目<b>唯一</b>允许出现
 * 厂商原始字段名的地方。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
public interface BillRecordProvider {

    /**
     * 判断本 Provider 是否负责该厂商。
     *
     * @param provider 厂商标识（小写）
     * @return true 表示由本 Provider 处理
     */
    boolean supports(String provider);

    /**
     * 本 Provider 对应的厂商账单数据当前是否可用。
     *
     * @return true 表示可查询到数据来源表
     */
    boolean isAvailable();

    /**
     * 归一化查询结果。
     *
     * @param condition 查询条件
     * @return 统一账单记录列表；无数据返回空列表（不为 null）
     */
    List<UnifiedBillRecord> toUnified(BillQueryCondition condition);
}
