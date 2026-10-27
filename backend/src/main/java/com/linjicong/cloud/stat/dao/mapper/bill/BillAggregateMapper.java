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
package com.linjicong.cloud.stat.dao.mapper.bill;

import com.linjicong.cloud.stat.service.bill.bo.BillQueryCondition;
import com.linjicong.cloud.stat.service.bill.bo.RowBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 统一账单聚合 Mapper（全部走手写 XML，架构决策 D1/D2/D9）。
 *
 * <p>跨三张物理表 UNION ALL 投影，MyBatis-Plus 的 {@code Wrapper} 只能操作单表，
 * 因此必须手写 SQL。注意：手写 SQL 不经过 {@code @TableLogic}，
 * XML 中每个 UNION 分支都显式写了 {@code deleted = 0}。</p>
 *
 * <p>所有入参使用 {@code @Param} 命名，XML 通过 {@code #{c.xxx}} / {@code #{limit}} 取值，
 * <b>全程零 {@code ${}} 拼接</b>；排序字段走 {@code <choose>} 白名单硬编码。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Repository
public interface BillAggregateMapper {

    /**
     * 区间汇总（KPI 主指标、环比上期、明细全量合计共用）。
     *
     * @param c 查询条件
     * @return 汇总行；无数据时各金额为 0、rowCount 为 0
     */
    RowBO.AggregateRow sumByRange(@Param("c") BillQueryCondition c);

    /**
     * 按月汇总（只返回有数据的月份，Java 侧按月份骨架补 0）。
     *
     * @param c 查询条件
     * @return 月份汇总行列表
     */
    List<RowBO.MonthSumRow> sumByMonth(@Param("c") BillQueryCondition c);

    /**
     * 按「厂商 × 月份」汇总（趋势堆叠柱数据源）。
     *
     * @param c 查询条件
     * @return 厂商月份汇总行列表
     */
    List<RowBO.ProviderMonthRow> sumByProviderAndMonth(@Param("c") BillQueryCondition c);

    /**
     * 按账号汇总（账号维度表、KPI 账号数统计）。
     *
     * @param c 查询条件
     * @return 账号汇总行列表
     */
    List<RowBO.AccountSumRow> sumByAccount(@Param("c") BillQueryCondition c);

    /**
     * 按产品汇总（产品 TOP 分析）。
     *
     * @param c        查询条件
     * @param dimension {@code provider} 或 {@code account}
     * @return 产品汇总行列表，按实付降序
     */
    List<RowBO.ProductSumRow> sumByProduct(@Param("c") BillQueryCondition c,
                                           @Param("dimension") String dimension);

    /**
     * 明细分页查询（服务端 LIMIT/OFFSET，决策 D2）。
     *
     * @param c      查询条件（含排序字段与方向）
     * @param offset 偏移量
     * @param limit  每页条数
     * @return 明细行列表
     */
    List<RowBO.DetailRow> pageDetail(@Param("c") BillQueryCondition c,
                                     @Param("offset") int offset,
                                     @Param("limit") int limit);

    /**
     * 明细总条数（独立 COUNT(*)，决策 D9）。
     *
     * @param c 查询条件
     * @return 总条数
     */
    long countDetail(@Param("c") BillQueryCondition c);

    /**
     * 明细全量合计（当前筛选条件下的 SUM，不是当前页）。
     *
     * @param c 查询条件
     * @return 汇总行
     */
    RowBO.AggregateRow sumDetail(@Param("c") BillQueryCondition c);

    /**
     * 查询 cloud_conf 账号主数据（用于账号总数统计、无账单账号补位、厂商可用性判断）。
     *
     * <p><b>为什么手写而不复用 {@code CloudConfMapper.selectList}？</b>
     * 本项目把 {@code CommonSqlInjector} 注册成了普通 {@code @Bean} 而未挂到
     * {@code GlobalConfig} 上，导致 MyBatis-Plus 的通用方法（{@code selectList} 等）
     * 实际未被注入，运行期会抛 {@code Invalid bound statement (not found)}。
     * 本模块不修改既有基础设施，改为在此显式声明所需 SQL，行为可控且与既有代码零冲突。</p>
     *
     * @param providers 厂商 WHERE 条件片段（传 null 表示不过滤）
     * @param accounts  账号 WHERE 条件片段（传 null 表示不过滤）
     * @return 账号主数据行
     */
    List<RowBO.CloudConfRow> selectCloudConfs(@Param("providers") List<String> providers,
                                             @Param("accounts") List<String> accounts);

    /**
     * 统计账期无法归一（month 非法且 pay_time 也提不出合法账期）的腾讯云账单行数。
     *
     * <p>数据质量指标专用。必须在 SQL 侧 COUNT，<b>不能</b>把全表实体加载进 JVM 数行数
     * （账单表可达百万级，为一个展示指标做全量 selectList 是内存隐患）。</p>
     *
     * @param c 查询条件（仅取账号白名单过滤）
     * @return 无法归一的行数
     */
    long countQcloudUnnormalizeableRows(@Param("c") BillQueryCondition c);

    /**
     * 统计腾讯云账单中金额列「真解析失败」的行数（数据质量指标 amountAnomalyCount）。
     *
     * <p>OBS-A 修复：HTTP 请求路径的金额解析全部在 SQL 侧 CAST（解析失败只产生 MySQL
     * warning），Java 侧 {@code BillAmountParser} 计数器在该路径不可达，故异常行数必须
     * 在 SQL 侧 COUNT，否则 {@code dataQuality.amountAnomalyCount} 恒为 0（死指标）。</p>
     *
     * <p>判据与 {@code BillAmountParser.toDecimal} 语义严格对齐：先做 TRIM 与千分位去逗号，
     * 空串 / {@code '-'} / {@code '--'} 属「合法缺失」不计入；剩余值不符合
     * {@code BigDecimal} 可解析格式（支持符号、小数、科学计数法）才计一行。
     * 覆盖投影中全部 5 个 CAST 金额列，任一列异常即计该行。</p>
     *
     * @param c 查询条件（仅取账号白名单过滤）
     * @return 存在金额解析异常的腾讯云账单行数
     */
    long countQcloudAnomalyRows(@Param("c") BillQueryCondition c);
}
