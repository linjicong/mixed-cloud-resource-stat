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
package com.linjicong.cloud.stat.service.bill.bo;

import com.linjicong.cloud.stat.service.bill.util.BillMonthUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一账单查询条件。
 *
 * <p>由 Controller 把原始 HTTP 参数收敛为本对象，Mapper XML 直接消费其属性
 * （{@code #{c.startMonth}} 等），因此字段命名与 XML 强耦合，不得随意重命名。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillQueryCondition {

    /** 账期起（含），{@code YYYY-MM} */
    private String startMonth;

    /** 账期止（含），{@code YYYY-MM} */
    private String endMonth;

    /** 厂商白名单，空 = 不限 */
    @Builder.Default
    private List<String> providers = new ArrayList<>();

    /** 账号白名单（conf_name），空 = 不限 */
    @Builder.Default
    private List<String> accounts = new ArrayList<>();

    /** 产品大类精确筛选 */
    private String productMain;

    /** 计费模式统一文本精确筛选 */
    private String chargingMode;

    /** 排序字段，白名单：payAmount / officialAmount / billMonth / tradeTime */
    private String sortField;

    /** 排序方向：asc / desc */
    private String sortOrder;

    /**
     * 账期无法归一而被丢弃的行数（Provider 归一化过程中累加，用于 dataQuality.droppedRowCount）。
     * 该字段不参与 SQL，仅作结果载体。
     */
    @Builder.Default
    private Integer droppedRowCount = 0;

    /**
     * 累加被丢弃的行数。
     *
     * @param count 本次丢弃行数
     */
    public void addDroppedRowCount(int count) {
        if (count <= 0) {
            return;
        }
        this.droppedRowCount = (this.droppedRowCount == null ? 0 : this.droppedRowCount) + count;
    }

    /**
     * 派生出同条件的另一账期区间（用于环比、sparkline 等跨窗口查询）。
     *
     * @param newStart 新区间起
     * @param newEnd   新区间止
     * @return 新的条件对象，其余字段保持不变
     */
    public BillQueryCondition withRange(String newStart, String newEnd) {
        return BillQueryCondition.builder()
                .startMonth(newStart)
                .endMonth(newEnd)
                .providers(this.providers == null ? new ArrayList<>() : new ArrayList<>(this.providers))
                .accounts(this.accounts == null ? new ArrayList<>() : new ArrayList<>(this.accounts))
                .productMain(this.productMain)
                .chargingMode(this.chargingMode)
                .sortField(this.sortField)
                .sortOrder(this.sortOrder)
                .droppedRowCount(this.droppedRowCount == null ? 0 : this.droppedRowCount)
                .build();
    }

    /**
     * 等长前置区间的起点。
     *
     * @return {@code YYYY-MM}；当前条件区间非法时返回 null
     */
    public String previousStartMonth() {
        String[] range = BillMonthUtil.previousRange(this.startMonth, this.endMonth);
        return range == null ? null : range[0];
    }

    /**
     * 等长前置区间的终点。
     *
     * @return {@code YYYY-MM}；当前条件区间非法时返回 null
     */
    public String previousEndMonth() {
        String[] range = BillMonthUtil.previousRange(this.startMonth, this.endMonth);
        return range == null ? null : range[1];
    }

    /**
     * 判断条件是否包含指定厂商。
     *
     * <p>语义：{@code providers} 为空表示「全部厂商」，此时返回 true。
     * XML 里用 {@code <if test="c.hasProvider('huawei')">} 决定是否拼接该厂商的 UNION 分支。</p>
     *
     * @param provider 厂商标识
     * @return true 表示需要查询该厂商
     */
    public boolean hasProvider(String provider) {
        if (this.providers == null || this.providers.isEmpty()) {
            return true;
        }
        if (provider == null) {
            return false;
        }
        return this.providers.contains(provider);
    }

    /**
     * 判断条件是否限定了账号白名单。
     *
     * @return true 表示 accounts 非空且需要拼接 IN 条件
     */
    public boolean hasAccounts() {
        return this.accounts != null && !this.accounts.isEmpty();
    }
}
