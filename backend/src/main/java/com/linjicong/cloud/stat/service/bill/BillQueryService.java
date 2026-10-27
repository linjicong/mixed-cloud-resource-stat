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
package com.linjicong.cloud.stat.service.bill;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linjicong.cloud.stat.controller.dto.bill.BillAccountVO;
import com.linjicong.cloud.stat.controller.dto.bill.BillDetailPageVO;
import com.linjicong.cloud.stat.controller.dto.bill.BillKpiVO;
import com.linjicong.cloud.stat.controller.dto.bill.BillProductTopVO;
import com.linjicong.cloud.stat.controller.dto.bill.BillProviderAvailabilityVO;
import com.linjicong.cloud.stat.controller.dto.bill.BillTrendVO;
import com.linjicong.cloud.stat.dao.mapper.bill.BillAggregateMapper;
import com.linjicong.cloud.stat.service.bill.bo.AmountSourceEnum;
import com.linjicong.cloud.stat.service.bill.bo.BillQueryCondition;
import com.linjicong.cloud.stat.service.bill.bo.ChargingModeEnum;
import com.linjicong.cloud.stat.service.bill.bo.RowBO;
import com.linjicong.cloud.stat.service.bill.provider.BillRecordProvider;
import com.linjicong.cloud.stat.service.bill.util.BillAmountParser;
import com.linjicong.cloud.stat.service.bill.util.BillMonthUtil;
import com.linjicong.cloud.stat.service.bill.util.RatioCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 统一账单编排服务。
 *
 * <p>职责：参数校验与归一、构造 {@link BillQueryCondition}、调用
 * {@link BillAggregateMapper} 完成 SQL 聚合，内存里只做「占比 / 环比 / TOP 截断 / 补 0」
 * 这类无法单条 SQL 表达的二次计算（架构决策 D1）。</p>
 *
 * <p><b>硬约束</b>：本类不允许出现任何厂商原始列名（如华为云的现金、账期列名，
 * 腾讯云的现金支付、原价列名等），那些只允许出现在
 * {@code service.bill.provider} 包与 Mapper XML 中。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Slf4j
@Service
public class BillQueryService {

    /** 厂商标识：华为云 */
    public static final String PROVIDER_HUAWEI = "huawei";

    /** 厂商标识：腾讯云 */
    public static final String PROVIDER_TENCENT = "tencent";

    /** 厂商标识：阿里云 */
    public static final String PROVIDER_ALIYUN = "aliyun";

    /** 支持账单数据的厂商集合（架构决策 D7，阿里云上线后在此加入） */
    public static final Set<String> SUPPORTED_PROVIDERS = Set.of(PROVIDER_HUAWEI, PROVIDER_TENCENT);

    /** 厂商中文标签 */
    public static final Map<String, String> PROVIDER_LABEL = Map.of(
            PROVIDER_HUAWEI, "华为云",
            PROVIDER_TENCENT, "腾讯云",
            PROVIDER_ALIYUN, "阿里云"
    );

    /**
     * cloud_conf.provider 的原始取值 → 本模块统一厂商标识。
     *
     * <p><b>实测事实</b>：{@code cloud_conf.provider} 存的是 SDK 风格的大小写驼峰
     * （{@code HCloud} / {@code QCloud} / {@code ACloud}），而账单表的
     * {@code conf_provider} 与前端契约用的是小写（{@code huawei} / {@code tencent} / {@code aliyun}）。
     * 这里必须做双向兼容，否则账号数统计与厂商可用性判断会全部落空。</p>
     */
    private static final Map<String, String> PROVIDER_ALIAS = Map.of(
            "huawei", PROVIDER_HUAWEI,
            "hcloud", PROVIDER_HUAWEI,
            "tencent", PROVIDER_TENCENT,
            "qcloud", PROVIDER_TENCENT,
            "aliyun", PROVIDER_ALIYUN,
            "acloud", PROVIDER_ALIYUN
    );

    /** 厂商中文标签（有序，用于图表堆叠顺序稳定） */
    private static final List<String> PROVIDER_ORDER = List.of(PROVIDER_HUAWEI, PROVIDER_TENCENT, PROVIDER_ALIYUN);

    /** 明细排序字段白名单（决策 D11） */
    private static final Set<String> SORT_FIELD_WHITELIST = Set.of("payAmount", "officialAmount", "billMonth", "tradeTime");

    /** 默认排序字段 */
    private static final String DEFAULT_SORT_FIELD = "payAmount";

    /** 默认排序方向 */
    private static final String DEFAULT_SORT_ORDER = "desc";

    /** 每页条数上限（决策 D10） */
    private static final int MAX_PAGE_SIZE = 200;

    /** 默认每页条数 */
    private static final int DEFAULT_PAGE_SIZE = 20;

    /** 账号表 sparkline 固定近 6 个月 */
    private static final int SPARKLINE_MONTHS = 6;

    /** 趋势窗口允许值 */
    private static final Set<Integer> TREND_MONTH_OPTIONS = Set.of(6, 12);

    /** 产品 TOP 允许值 */
    private static final Set<Integer> PRODUCT_TOP_OPTIONS = Set.of(5, 10, 20);

    private final BillAggregateMapper aggregateMapper;

    /**
     * 归一化 Provider 列表（架构文档规定的 SPI 扩展点，按 D7 / §1.3 保留）。
     *
     * <p><b>当前状态（OBS-B 显式标注）</b>：HTTP 请求路径的数据质量统计已改为
     * SQL 侧 COUNT（见 {@code countQcloudAnomalyRows} /
     * {@code countQcloudUnnormalizeableRows}），本字段目前<b>没有业务调用方</b>，
     * 仅为两个后续场景预留接线点：① break_down 明细补充源实装（OBS-1，待 PM/架构
     * 定夺口径）时在此注入逐行归一化来源；② 未来按行读取 / 导出口径复用
     * {@code Provider.toUnified}。删除注入反而会让接入方漏掉该扩展点，故保留。
     * Provider 类本身不是死代码：它们是全项目唯一允许出现厂商原始字段名的归一化层，
     * 其映射逻辑与 Mapper XML 的投影列一一对应，是口径的文档化载体。</p>
     */
    private final List<BillRecordProvider> billRecordProviders;

    public BillQueryService(BillAggregateMapper aggregateMapper,
                            List<BillRecordProvider> billRecordProviders) {
        this.aggregateMapper = aggregateMapper;
        this.billRecordProviders = billRecordProviders == null ? List.of() : billRecordProviders;
    }

    // ==================================================================
    // 1. KPI
    // ==================================================================

    /**
     * 查询总览 KPI。
     *
     * @param condition 已归一的查询条件
     * @return KPI VO
     */
    public BillKpiVO queryKpi(BillQueryCondition condition) {
        normalizeRange(condition);
        // 账期无法归一的行数：SQL 侧 COUNT（只统计，不参与聚合）
        int droppedRowCount = countDroppedRows(condition);
        // 金额解析异常行数：请求路径金额全在 SQL 侧 CAST，Java 计数器不可达，
        // 同样必须在 SQL 侧 COUNT（OBS-A 修复，避免该指标恒为 0 成为死指标）
        int amountAnomalyCount = countAnomalyRows(condition);

        // 本期聚合
        RowBO.AggregateRow current = safeAggregate(condition);

        // 上期聚合（等长前置区间）
        String[] prevRange = BillMonthUtil.previousRange(condition.getStartMonth(), condition.getEndMonth());
        RowBO.AggregateRow previous = (prevRange == null)
                ? emptyAggregate()
                : safeAggregate(condition.withRange(prevRange[0], prevRange[1]));

        // 账号维度（本期有账单的账号，SQL 已按 (account, provider) 分组去重）
        List<RowBO.AccountSumRow> accountRows = safeList(aggregateMapper.sumByAccount(condition));

        // cloud_conf 账号主数据（含停用与无账单账号）
        List<RowBO.CloudConfRow> confs = queryCloudConfs(condition.getProviders(), condition.getAccounts());

        // BUG-1 修复：KPI 账号计数与 /bill/accounts 列表严格同源、同判据。
        //   去重口径统一为 accountKey(账号名, 归一厂商)，与 queryAccounts 的 rowMap 构建完全一致：
        //     · 账号全集 = 账单聚合出的账号（sumByAccount，天然含 huawei 等 cloud_conf 里没有的名称）
        //                  ∪ cloud_conf 主数据账号（含无账单/停用账号），按 accountKey 去重取并集；
        //     · 「有账单」判据 = 该账号区间内出现在 sumByAccount 结果中，与金额是否为 0 无关
        //                  （有账单但净额恰被退款抵消为 0 的账号仍算有账单，不再漏计）。
        //   由此保证 withBill + withoutBill == total，且 total 恒等于 /bill/accounts 返回行数。
        AccountCountSummary accountSummary = summarizeAccounts(accountRows, confs);
        int accountTotalCount = accountSummary.total();
        int accountWithBillCount = accountSummary.withBill();
        int accountWithoutBillCount = accountSummary.withoutBill();

        // TOP 产品
        List<RowBO.ProductSumRow> productRows = safeList(aggregateMapper.sumByProduct(condition, "provider"));
        BillKpiVO.TopProduct topProduct = buildTopProduct(productRows, current.getPayAmount());

        // 环比
        RatioCalculator.RatioResult ratioResult = RatioCalculator.chainRatio(
                current.getPayAmount(), previous.getPayAmount());
        BillKpiVO.Range previousRange = (prevRange == null) ? null : BillKpiVO.Range.builder()
                .startMonth(prevRange[0])
                .endMonth(prevRange[1])
                .monthCount(BillMonthUtil.countMonths(prevRange[0], prevRange[1]))
                .build();

        BillKpiVO.ChainRatio chainRatio = BillKpiVO.ChainRatio.builder()
                .rate(ratioResult.getRate())
                .comparable(ratioResult.isComparable())
                .previousRange(previousRange)
                .previousPayAmount(BillAmountParser.scale(previous.getPayAmount()))
                .build();

        BigDecimal discountRate = RatioCalculator.discountRate(
                current.getDiscountAmount(), current.getOfficialAmount());

        BillKpiVO.DataQuality dataQuality = BillKpiVO.DataQuality.builder()
                .amountAnomalyCount(amountAnomalyCount)
                .droppedRowCount(droppedRowCount)
                .build();

        return BillKpiVO.builder()
                .range(buildRange(condition))
                .totalPayAmount(BillAmountParser.scale(current.getPayAmount()))
                .totalOfficialAmount(BillAmountParser.scale(current.getOfficialAmount()))
                .totalDiscountAmount(BillAmountParser.scale(current.getDiscountAmount()))
                .discountRate(discountRate)
                .chainRatio(chainRatio)
                .accountTotalCount(accountTotalCount)
                .accountWithBillCount(accountWithBillCount)
                .accountWithoutBillCount(accountWithoutBillCount)
                .topProduct(topProduct)
                .providerAvailability(buildProviderAvailability(condition.getProviders()))
                .dataQuality(dataQuality)
                .currentMonthInProgress(BillMonthUtil.currentMonth().equals(condition.getEndMonth()))
                .build();
    }

    /**
     * 组装 TOP 产品摘要。
     *
     * @param productRows 产品汇总行（已按实付降序）
     * @param totalPay    区间实付合计
     * @return TOP 产品；无数据时返回 null
     */
    private BillKpiVO.TopProduct buildTopProduct(List<RowBO.ProductSumRow> productRows, BigDecimal totalPay) {
        if (productRows.isEmpty()) {
            return null;
        }
        RowBO.ProductSumRow first = productRows.get(0);
        return BillKpiVO.TopProduct.builder()
                .productMain(first.getProductMain())
                .provider(first.getProvider())
                .providerLabel(labelOf(first.getProvider()))
                .payAmount(BillAmountParser.scale(first.getPayAmount()))
                .ratio(RatioCalculator.ratio(first.getPayAmount(), totalPay))
                .build();
    }

    /**
     * 统计账期无法归一而被丢弃的行数（数据质量指标）。
     *
     * <p><b>SQL 侧 COUNT</b>：账单表数据量可达百万级，绝不允许为了一个展示指标
     * 把全表实体加载进 JVM（此前的 Provider.toUnified 全量扫描方案已废弃）。
     * 统计对象是腾讯云账单表中 {@code month} 列与 {@code pay_time} 都提不出合法
     * {@code YYYY-MM} 的行；华为云两张表的账期列由 SDK 产出，格式稳定且非法值在
     * SQL 聚合时天然为 NULL 不参与分组，无需额外统计。
     * 统计失败时降级为 0，不影响主流程。</p>
     *
     * @param condition 查询条件
     * @return 被丢弃的行数
     */
    private int countDroppedRows(BillQueryCondition condition) {
        try {
            return (int) aggregateMapper.countQcloudUnnormalizeableRows(condition);
        } catch (RuntimeException e) {
            log.warn("统计腾讯云账期丢弃行数失败，按 0 处理", e);
            return 0;
        }
    }

    /**
     * 统计金额解析异常的账单行数（数据质量指标 amountAnomalyCount）。
     *
     * <p>OBS-A 修复：请求路径上金额解析全部发生在 SQL 侧 CAST，
     * {@code BillAmountParser} 的线程内计数器在该路径不可达；若继续读它，
     * {@code dataQuality.amountAnomalyCount} 会恒为 0（PRD「脏金额告警」能力失效）。
     * 改为与投影 {@code qcAmount} 片段同口径的 SQL 侧 COUNT
     * （非法格式 = TRIM 去逗号后既非空/占位符、也不符合 {@code BigDecimal} 文法）。
     * 统计失败时降级为 0，不影响主流程。</p>
     *
     * @param condition 查询条件
     * @return 存在金额解析异常的行数
     */
    private int countAnomalyRows(BillQueryCondition condition) {
        try {
            return (int) aggregateMapper.countQcloudAnomalyRows(condition);
        } catch (RuntimeException e) {
            log.warn("统计腾讯云金额解析异常行数失败，按 0 处理", e);
            return 0;
        }
    }

    // ==================================================================
    // 2. 趋势
    // ==================================================================

    /**
     * 查询历史趋势。
     *
     * @param condition 查询条件
     * @param months    窗口月数，只接受 6 或 12，其它值按 6 处理
     * @return 趋势 VO
     */
    public BillTrendVO queryTrend(BillQueryCondition condition, Integer months) {
        normalizeRange(condition);
        int windowMonths = (months != null && TREND_MONTH_OPTIONS.contains(months)) ? months : 6;

        // 以 endMonth 为基准向前推 months-1 个月，忽略传入的 startMonth
        String endMonth = condition.getEndMonth();
        String startMonth = BillMonthUtil.shiftMonth(endMonth, -(windowMonths - 1));
        if (startMonth == null) {
            throw new IllegalArgumentException("账期格式非法，无法计算趋势窗口");
        }
        BillQueryCondition windowCondition = condition.withRange(startMonth, endMonth);

        List<String> monthSkeleton = BillMonthUtil.monthRange(startMonth, endMonth);
        List<RowBO.ProviderMonthRow> rows = safeList(aggregateMapper.sumByProviderAndMonth(windowCondition));

        // provider × month 索引，便于补 0
        Map<String, Map<String, BigDecimal>> index = new LinkedHashMap<>();
        for (RowBO.ProviderMonthRow row : rows) {
            if (row.getProvider() == null || row.getBillMonth() == null) {
                continue;
            }
            index.computeIfAbsent(row.getProvider(), k -> new LinkedHashMap<>())
                    .merge(row.getBillMonth(), BillAmountParser.nullToZero(row.getPayAmount()), BigDecimal::add);
        }

        List<String> effectiveProviders = effectiveProviders(condition.getProviders());
        List<BillTrendVO.ProviderSeries> series = new ArrayList<>();
        List<BigDecimal> totalAmounts = new ArrayList<>();

        // 合计骨架先填 0
        for (int i = 0; i < monthSkeleton.size(); i++) {
            totalAmounts.add(BigDecimal.ZERO);
        }

        for (String provider : effectiveProviders) {
            List<BigDecimal> amounts = new ArrayList<>();
            Map<String, BigDecimal> providerIndex = index.getOrDefault(provider, Map.of());
            for (int i = 0; i < monthSkeleton.size(); i++) {
                BigDecimal value = BillAmountParser.scale(
                        providerIndex.getOrDefault(monthSkeleton.get(i), BigDecimal.ZERO));
                amounts.add(value);
                totalAmounts.set(i, totalAmounts.get(i).add(value));
            }
            series.add(BillTrendVO.ProviderSeries.builder()
                    .provider(provider)
                    .label(labelOf(provider))
                    .amounts(amounts)
                    .build());
        }

        // 合计统一精度
        List<BigDecimal> scaledTotals = new ArrayList<>(totalAmounts.size());
        for (BigDecimal total : totalAmounts) {
            scaledTotals.add(BillAmountParser.scale(total));
        }

        // 环比：第一个恒为 null，上期为 0 也为 null
        List<BigDecimal> chainRatios = new ArrayList<>(scaledTotals.size());
        for (int i = 0; i < scaledTotals.size(); i++) {
            if (i == 0) {
                chainRatios.add(null);
            } else {
                RatioCalculator.RatioResult result = RatioCalculator.chainRatio(
                        scaledTotals.get(i), scaledTotals.get(i - 1));
                chainRatios.add(result.isComparable() ? result.getRate() : null);
            }
        }

        boolean hasData = scaledTotals.stream().anyMatch(v -> !BillAmountParser.isZero(v));

        return BillTrendVO.builder()
                .range(BillKpiVO.Range.builder()
                        .startMonth(startMonth)
                        .endMonth(endMonth)
                        .monthCount(monthSkeleton.size())
                        .build())
                .months(monthSkeleton)
                .series(series)
                .totalAmounts(scaledTotals)
                .chainRatios(chainRatios)
                .providerAvailability(buildProviderAvailability(condition.getProviders()))
                .hasData(hasData)
                .build();
    }

    // ==================================================================
    // 3. 账号维度
    // ==================================================================

    /**
     * 查询账号维度数据。
     *
     * @param condition 查询条件
     * @param topN      占比图「其他」合并阈值，仅作标记，rows 始终全量返回
     * @return 账号维度 VO
     */
    public BillAccountVO queryAccounts(BillQueryCondition condition, Integer topN) {
        normalizeRange(condition);

        // 本期账号汇总
        List<RowBO.AccountSumRow> currentRows = safeList(aggregateMapper.sumByAccount(condition));

        // 上期账号汇总
        String[] prevRange = BillMonthUtil.previousRange(condition.getStartMonth(), condition.getEndMonth());
        List<RowBO.AccountSumRow> previousRows = (prevRange == null)
                ? List.of()
                : safeList(aggregateMapper.sumByAccount(condition.withRange(prevRange[0], prevRange[1])));

        Map<String, BigDecimal> previousIndex = new LinkedHashMap<>();
        for (RowBO.AccountSumRow row : previousRows) {
            previousIndex.merge(accountKey(row.getAccount(), row.getProvider()),
                    BillAmountParser.nullToZero(row.getPayAmount()), BigDecimal::add);
        }

        // sparkline：固定近 6 个月，以 endMonth 为基准
        String sparkStart = BillMonthUtil.shiftMonth(condition.getEndMonth(), -(SPARKLINE_MONTHS - 1));
        List<String> sparkMonths = sparkStart == null
                ? List.of()
                : BillMonthUtil.monthRange(sparkStart, condition.getEndMonth());

        // 构建 sparkline 索引：accountKey -> month -> amount
        Map<String, Map<String, BigDecimal>> sparkIndex = buildSparkIndex(condition, sparkMonths);

        // cloud_conf 补全（含无账单账号）
        List<RowBO.CloudConfRow> confs = queryCloudConfs(condition.getProviders(), condition.getAccounts());

        // 本期有账单的账号
        Map<String, BillAccountVO.Row> rowMap = new LinkedHashMap<>();
        for (RowBO.AccountSumRow sum : currentRows) {
            String key = accountKey(sum.getAccount(), sum.getProvider());
            BillAccountVO.Row row = buildAccountRow(sum, previousIndex.get(key),
                    sparkIndex.getOrDefault(key, Map.of()), sparkMonths);
            rowMap.put(key, row);
        }

        // 补齐 cloud_conf 中存在但本期无账单的账号（如阿里云、或区间内无消费账号）
        for (RowBO.CloudConfRow conf : confs) {
            String account = conf.getName();
            String provider = normalizeProvider(conf.getProvider());
            if (account == null || provider == null) {
                continue;
            }
            String key = accountKey(account, provider);
            if (rowMap.containsKey(key)) {
                BillAccountVO.Row existing = rowMap.get(key);
                existing.setEnabled(true);
                if (existing.getRegion() == null || existing.getRegion().isBlank()) {
                    existing.setRegion(conf.getRegion());
                }
                continue;
            }
            rowMap.put(key, BillAccountVO.Row.builder()
                    .account(account)
                    .provider(provider)
                    .providerLabel(labelOf(provider))
                    .region(conf.getRegion())
                    .enabled(true)
                    .payAmount(BillAmountParser.scale(BigDecimal.ZERO))
                    .officialAmount(BillAmountParser.scale(BigDecimal.ZERO))
                    .discountAmount(BillAmountParser.scale(BigDecimal.ZERO))
                    .ratio(BigDecimal.ZERO.setScale(BillAmountParser.SCALE, RoundingMode.HALF_UP))
                    .chainRatio(BillAccountVO.ChainRatio.builder()
                            .rate(null)
                            .comparable(false)
                            .previousPayAmount(BillAmountParser.scale(BigDecimal.ZERO))
                            .build())
                    .hasBillData(false)
                    .sparkline(new ArrayList<>())
                    .sparklineMonths(new ArrayList<>())
                    .build());
        }

        // 占比分母 = 只累计 hasBillData=true 的账号
        BigDecimal ratioDenominator = rowMap.values().stream()
                .filter(row -> Boolean.TRUE.equals(row.getHasBillData()))
                .map(BillAccountVO.Row::getPayAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal denominator = BillAmountParser.scale(ratioDenominator);

        for (BillAccountVO.Row row : rowMap.values()) {
            if (Boolean.TRUE.equals(row.getHasBillData())) {
                row.setRatio(RatioCalculator.ratio(row.getPayAmount(), denominator));
            } else {
                row.setRatio(BigDecimal.ZERO.setScale(BillAmountParser.SCALE, RoundingMode.HALF_UP));
            }
        }

        // 排序：有账单的按实付降序在前，无账单的排最后
        List<BillAccountVO.Row> rows = new ArrayList<>(rowMap.values());
        rows.sort(Comparator
                .comparing((BillAccountVO.Row row) -> Boolean.TRUE.equals(row.getHasBillData()) ? 0 : 1)
                .thenComparing(BillAccountVO.Row::getPayAmount, Comparator.reverseOrder())
                .thenComparing(BillAccountVO.Row::getAccount, Comparator.nullsLast(Comparator.naturalOrder())));

        // 按厂商汇总
        List<BillAccountVO.ProviderShare> byProvider = buildProviderShare(rows, denominator);

        BillKpiVO.Range previousRange = (prevRange == null) ? null : BillKpiVO.Range.builder()
                .startMonth(prevRange[0])
                .endMonth(prevRange[1])
                .monthCount(BillMonthUtil.countMonths(prevRange[0], prevRange[1]))
                .build();

        return BillAccountVO.builder()
                .range(BillAccountVO.Range.builder()
                        .startMonth(condition.getStartMonth())
                        .endMonth(condition.getEndMonth())
                        .monthCount(BillMonthUtil.countMonths(condition.getStartMonth(), condition.getEndMonth()))
                        .previousRange(previousRange)
                        .build())
                .totalPayAmount(denominator)
                .rows(rows)
                .byProvider(byProvider)
                .build();
    }

    /**
     * 构建 sparkline 索引：accountKey → month → 金额。
     *
     * <p>实现方式：对 sparkline 窗口内的每个月分别查询一次账号汇总，
     * 这样既复用了同一条 SQL（无 N+1 放大到每账号一请求），又避免新增 Mapper 方法。
     * 窗口固定 6 个月，即最多 6 次轻量聚合查询。</p>
     *
     * <p>注意：sparkline 口径固定为「以 endMonth 为基准的近 6 个月」，因此这里
     * 每次查询都用单月区间构造新条件，<b>不能</b>在原有区间条件上叠加
     * （否则 endMonth 早于 startMonth 会导致一条数据都查不出来）。</p>
     *
     * @param condition 基础条件（仅取其厂商/账号筛选）
     * @param months    月份骨架
     * @return 索引
     */
    private Map<String, Map<String, BigDecimal>> buildSparkIndex(BillQueryCondition condition, List<String> months) {
        Map<String, Map<String, BigDecimal>> index = new LinkedHashMap<>();
        for (String month : months) {
            BillQueryCondition single = BillQueryCondition.builder()
                    .startMonth(month)
                    .endMonth(month)
                    .providers(condition.getProviders() == null
                            ? new ArrayList<>() : new ArrayList<>(condition.getProviders()))
                    .accounts(condition.getAccounts() == null
                            ? new ArrayList<>() : new ArrayList<>(condition.getAccounts()))
                    .build();
            List<RowBO.AccountSumRow> rows = safeList(aggregateMapper.sumByAccount(single));
            for (RowBO.AccountSumRow row : rows) {
                String key = accountKey(row.getAccount(), row.getProvider());
                index.computeIfAbsent(key, k -> new LinkedHashMap<>())
                        .merge(month, BillAmountParser.nullToZero(row.getPayAmount()), BigDecimal::add);
            }
        }
        return index;
    }

    /**
     * 构造账号行。
     *
     * @param sum            本期汇总行
     * @param previousAmount 上期实付
     * @param sparkMap       该账号的月份金额索引
     * @param sparkMonths    sparkline 月份骨架
     * @return 账号行
     */
    private BillAccountVO.Row buildAccountRow(RowBO.AccountSumRow sum,
                                              BigDecimal previousAmount,
                                              Map<String, BigDecimal> sparkMap,
                                              List<String> sparkMonths) {
        BigDecimal payAmount = BillAmountParser.scale(sum.getPayAmount());
        RatioCalculator.RatioResult ratioResult = RatioCalculator.chainRatio(payAmount, previousAmount);

        List<BigDecimal> sparkline = new ArrayList<>();
        List<String> sparklineMonths = new ArrayList<>();
        if (!sparkMonths.isEmpty() && !sparkMap.isEmpty()) {
            for (String month : sparkMonths) {
                sparkline.add(BillAmountParser.scale(sparkMap.getOrDefault(month, BigDecimal.ZERO)));
                sparklineMonths.add(month);
            }
        }

        return BillAccountVO.Row.builder()
                .account(sum.getAccount())
                .provider(normalizeProvider(sum.getProvider()))
                .providerLabel(labelOf(sum.getProvider()))
                .region(sum.getRegion())
                .enabled(true)
                .payAmount(payAmount)
                .officialAmount(BillAmountParser.scale(sum.getOfficialAmount()))
                .discountAmount(BillAmountParser.scale(sum.getDiscountAmount()))
                .ratio(BigDecimal.ZERO.setScale(BillAmountParser.SCALE, RoundingMode.HALF_UP))
                .chainRatio(BillAccountVO.ChainRatio.builder()
                        .rate(ratioResult.getRate())
                        .comparable(ratioResult.isComparable())
                        .previousPayAmount(BillAmountParser.scale(previousAmount))
                        .build())
                .hasBillData(true)
                .sparkline(sparkline)
                .sparklineMonths(sparklineMonths)
                .build();
    }

    /**
     * 按厂商汇总（占比环形图「按厂商」维度数据源）。
     *
     * @param rows        账号行
     * @param denominator 占比分母
     * @return 按厂商汇总列表
     */
    private List<BillAccountVO.ProviderShare> buildProviderShare(List<BillAccountVO.Row> rows,
                                                                 BigDecimal denominator) {
        Map<String, BigDecimal> sums = new LinkedHashMap<>();
        for (String provider : PROVIDER_ORDER) {
            sums.put(provider, BigDecimal.ZERO);
        }
        for (BillAccountVO.Row row : rows) {
            if (!Boolean.TRUE.equals(row.getHasBillData()) || row.getProvider() == null) {
                continue;
            }
            sums.merge(row.getProvider(), BillAmountParser.nullToZero(row.getPayAmount()), BigDecimal::add);
        }

        List<BillAccountVO.ProviderShare> shares = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : sums.entrySet()) {
            if (BillAmountParser.isZero(entry.getValue())) {
                // 无金额厂商不出现在占比图，避免图例出现 0 值扇区
                continue;
            }
            shares.add(BillAccountVO.ProviderShare.builder()
                    .provider(entry.getKey())
                    .label(labelOf(entry.getKey()))
                    .payAmount(BillAmountParser.scale(entry.getValue()))
                    .ratio(RatioCalculator.ratio(entry.getValue(), denominator))
                    .build());
        }
        shares.sort(Comparator.comparing(BillAccountVO.ProviderShare::getPayAmount, Comparator.reverseOrder()));
        return shares;
    }

    // ==================================================================
    // 4. 明细分页
    // ==================================================================

    /**
     * 查询明细分页。
     *
     * @param condition 查询条件
     * @param pageNum   页码，从 1 开始
     * @param pageSize  每页条数，服务端钳制 ≤200
     * @return 明细分页 VO
     */
    public BillDetailPageVO queryDetailPage(BillQueryCondition condition, Integer pageNum, Integer pageSize) {
        normalizeRange(condition);
        normalizeSort(condition);
        // 金额解析异常行数走 SQL 侧统计（同 KPI，Java 计数器在 SQL 聚合路径不可达）
        int amountAnomalyCount = countAnomalyRows(condition);

        int safePageNum = (pageNum == null || pageNum < 1) ? 1 : pageNum;
        int safePageSize = (pageSize == null || pageSize < 1) ? DEFAULT_PAGE_SIZE : Math.min(pageSize, MAX_PAGE_SIZE);
        int offset = (safePageNum - 1) * safePageSize;

        long total = aggregateMapper.countDetail(condition);
        if (total <= 0) {
            return BillDetailPageVO.builder()
                    .pageNum(safePageNum)
                    .pageSize(safePageSize)
                    .total(0L)
                    .pages(0)
                    .records(new ArrayList<>())
                    .summary(BillDetailPageVO.Summary.builder()
                            .payAmountSum(BillAmountParser.scale(BigDecimal.ZERO))
                            .officialAmountSum(BillAmountParser.scale(BigDecimal.ZERO))
                            .discountAmountSum(BillAmountParser.scale(BigDecimal.ZERO))
                            .build())
                    .dataQuality(BillDetailPageVO.DataQuality.builder()
                            .amountAnomalyCount(amountAnomalyCount)
                            .build())
                    .build();
        }

        List<RowBO.DetailRow> detailRows = safeList(aggregateMapper.pageDetail(condition, offset, safePageSize));
        List<BillDetailPageVO.Record> records = new ArrayList<>(detailRows.size());
        for (RowBO.DetailRow row : detailRows) {
            records.add(toDetailRecord(row));
        }

        RowBO.AggregateRow summary = safeAggregateDetail(condition);

        int pages = (int) ((total + safePageSize - 1) / safePageSize);

        return BillDetailPageVO.builder()
                .pageNum(safePageNum)
                .pageSize(safePageSize)
                .total(total)
                .pages(pages)
                .records(records)
                .summary(BillDetailPageVO.Summary.builder()
                        .payAmountSum(BillAmountParser.scale(summary.getPayAmount()))
                        .officialAmountSum(BillAmountParser.scale(summary.getOfficialAmount()))
                        .discountAmountSum(BillAmountParser.scale(summary.getDiscountAmount()))
                        .build())
                .dataQuality(BillDetailPageVO.DataQuality.builder()
                        .amountAnomalyCount(amountAnomalyCount)
                        .build())
                .build();
    }

    /**
     * 明细行 BO → VO。
     *
     * @param row 明细行
     * @return 明细 VO
     */
    private BillDetailPageVO.Record toDetailRecord(RowBO.DetailRow row) {
        AmountSourceEnum source = AmountSourceEnum.parseOrDefault(row.getAmountSource());
        String provider = normalizeProvider(row.getProvider());
        ChargingModeEnum mode = resolveChargingMode(provider, row.getChargingModeRaw());

        return BillDetailPageVO.Record.builder()
                .id(source.getIdPrefix() + ":" + (row.getSrcPk() == null ? "0" : row.getSrcPk()))
                .billMonth(row.getBillMonth())
                .account(row.getAccount())
                .provider(provider)
                .providerLabel(labelOf(provider))
                .productMain(row.getProductMain())
                .productSub(row.getProductSub())
                .chargingMode(mode.label())
                .officialAmount(BillAmountParser.scale(row.getOfficialAmount()))
                .consumeAmount(BillAmountParser.scale(row.getConsumeAmount()))
                .discountAmount(BillAmountParser.scale(row.getDiscountAmount()))
                .payAmount(BillAmountParser.scale(row.getPayAmount()))
                .cashPay(BillAmountParser.scale(row.getCashPay()))
                .voucherPay(BillAmountParser.scale(row.getVoucherPay()))
                .creditPay(BillAmountParser.scale(row.getCreditPay()))
                .debtAmount(BillAmountParser.scale(row.getDebtAmount()))
                .eraseAmount(BillAmountParser.scale(row.getEraseAmount()))
                .writeoffAmount(BillAmountParser.scale(row.getWriteoffAmount()))
                .region(row.getRegion())
                .resourceId(row.getResourceId())
                .resourceName(row.getResourceName())
                .tradeTime(row.getTradeTime())
                .amountSource(source.name())
                .amountSourceLabel(source.getLabel())
                .amountAnomaly(Boolean.FALSE)
                .build();
    }

    /**
     * 按厂商解析计费模式文本。
     *
     * @param provider 厂商标识
     * @param raw      原始值（华为编码字符串 / 腾讯文本）
     * @return 计费模式枚举
     */
    private ChargingModeEnum resolveChargingMode(String provider, String raw) {
        if (PROVIDER_HUAWEI.equals(provider)) {
            return ChargingModeEnum.fromHuaweiCodeText(raw);
        }
        if (PROVIDER_TENCENT.equals(provider)) {
            return ChargingModeEnum.fromTencentText(raw);
        }
        return ChargingModeEnum.OTHER;
    }

    // ==================================================================
    // 5. 产品 TOP
    // ==================================================================

    /**
     * 查询产品 TOP。
     *
     * @param condition 查询条件
     * @param topN      取前 N，只接受 5/10/20，其它值按 10
     * @param dimension provider 或 account，其它值按 provider
     * @return 产品 TOP VO
     */
    public BillProductTopVO queryProductTop(BillQueryCondition condition, Integer topN, String dimension) {
        normalizeRange(condition);

        int safeTopN = (topN != null && PRODUCT_TOP_OPTIONS.contains(topN)) ? topN : 10;
        String safeDimension = "account".equalsIgnoreCase(dimension) ? "account" : "provider";

        List<RowBO.ProductSumRow> allRows = safeList(aggregateMapper.sumByProduct(condition, safeDimension));

        BigDecimal totalPayAmount = allRows.stream()
                .map(row -> BillAmountParser.nullToZero(row.getPayAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalPayAmount = BillAmountParser.scale(totalPayAmount);

        // 后端 SQL 已按实付降序，这里再稳定排序一次
        allRows.sort(Comparator.comparing(
                (RowBO.ProductSumRow row) -> BillAmountParser.nullToZero(row.getPayAmount()),
                Comparator.reverseOrder()));

        List<BillProductTopVO.Row> rows = new ArrayList<>();
        BigDecimal rowsSum = BigDecimal.ZERO;
        BigDecimal othersSum = BigDecimal.ZERO;
        int othersCount = 0;

        for (int i = 0; i < allRows.size(); i++) {
            RowBO.ProductSumRow row = allRows.get(i);
            BigDecimal payAmount = BillAmountParser.scale(row.getPayAmount());
            if (i < safeTopN) {
                rowsSum = rowsSum.add(payAmount);
                rows.add(BillProductTopVO.Row.builder()
                        .rank(i + 1)
                        .productMain(row.getProductMain())
                        .provider(normalizeProvider(row.getProvider()))
                        .providerLabel(labelOf(row.getProvider()))
                        .account(row.getAccount())
                        .payAmount(payAmount)
                        .ratio(RatioCalculator.ratio(payAmount, totalPayAmount))
                        .accountCount(row.getAccountCount() == null ? 0L : row.getAccountCount())
                        .resourceCount(row.getResourceCount())
                        .build());
            } else {
                othersSum = othersSum.add(payAmount);
                othersCount++;
            }
        }

        // 闭环校验：rows + others = total，差值归入 others 以吸收精度误差
        BigDecimal diff = totalPayAmount.subtract(rowsSum).subtract(othersSum);
        if (diff.compareTo(BigDecimal.ZERO) != 0) {
            othersSum = othersSum.add(diff);
        }

        BillProductTopVO.Others others = BillProductTopVO.Others.builder()
                .payAmount(BillAmountParser.scale(othersSum))
                .ratio(RatioCalculator.ratio(othersSum, totalPayAmount))
                .productCount(othersCount)
                .build();

        return BillProductTopVO.builder()
                .range(buildRange(condition))
                .dimension(safeDimension)
                .totalPayAmount(totalPayAmount)
                .rows(rows)
                .others(others)
                .build();
    }

    // ==================================================================
    // 通用私有方法
    // ==================================================================

    /**
     * 校验并归一账期范围。
     *
     * @param condition 查询条件
     * @throws IllegalArgumentException 账期非法或起止倒序
     */
    private void normalizeRange(BillQueryCondition condition) {
        if (condition == null) {
            throw new IllegalArgumentException("查询条件不能为空");
        }
        String start = BillMonthUtil.normalize(condition.getStartMonth());
        String end = BillMonthUtil.normalize(condition.getEndMonth());
        if (start == null) {
            throw new IllegalArgumentException("账期格式非法: " + condition.getStartMonth()
                    + "，要求 YYYY-MM");
        }
        if (end == null) {
            throw new IllegalArgumentException("账期格式非法: " + condition.getEndMonth()
                    + "，要求 YYYY-MM");
        }
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("账期起不能晚于账期止: " + start + " > " + end);
        }
        if (BillMonthUtil.exceedsMaxSpan(start, end)) {
            throw new IllegalArgumentException("账期跨度不能超过 " + BillMonthUtil.MAX_MONTH_SPAN + " 个月");
        }
        condition.setStartMonth(start);
        condition.setEndMonth(end);
        // 厂商列表去重且只保留已知厂商
        condition.setProviders(effectiveProviders(condition.getProviders()));
        // 计费模式归一为统一标签
        condition.setChargingMode(normalizeChargingMode(condition.getChargingMode()));
    }

    /**
     * 计费模式筛选值归一（接受统一标签或厂商原文）。
     *
     * @param raw 原始筛选值
     * @return 统一中文标签；无法识别返回 null（表示不过滤）
     */
    private String normalizeChargingMode(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        ChargingModeEnum byLabel = ChargingModeEnum.fromLabel(raw);
        if (byLabel != null) {
            return byLabel.label();
        }
        ChargingModeEnum byTencentText = ChargingModeEnum.fromTencentText(raw);
        if (byTencentText != ChargingModeEnum.OTHER) {
            return byTencentText.label();
        }
        ChargingModeEnum byHuaweiCode = ChargingModeEnum.fromHuaweiCodeText(raw);
        if (byHuaweiCode != ChargingModeEnum.OTHER) {
            return byHuaweiCode.label();
        }
        return null;
    }

    /**
     * 排序参数白名单归一（决策 D11：非法值回落 payAmount desc）。
     *
     * @param condition 查询条件
     */
    private void normalizeSort(BillQueryCondition condition) {
        String field = condition.getSortField();
        if (field == null || !SORT_FIELD_WHITELIST.contains(field)) {
            field = DEFAULT_SORT_FIELD;
        }
        condition.setSortField(field);

        String order = condition.getSortOrder();
        if (order == null || (!"asc".equalsIgnoreCase(order) && !"desc".equalsIgnoreCase(order))) {
            order = DEFAULT_SORT_ORDER;
        }
        condition.setSortOrder(order.toLowerCase());
    }

    /**
     * 计算实际生效的厂商列表（去重 + 已知厂商过滤 + 稳定顺序）。
     *
     * @param providers 原始厂商列表
     * @return 生效厂商列表；入参为空时返回全部已知厂商
     */
    private List<String> effectiveProviders(List<String> providers) {
        if (providers == null || providers.isEmpty()) {
            return new ArrayList<>(PROVIDER_ORDER);
        }
        Set<String> normalized = new LinkedHashSet<>();
        for (String provider : providers) {
            String p = normalizeProvider(provider);
            if (p != null) {
                normalized.add(p);
            }
        }
        List<String> ordered = new ArrayList<>();
        for (String provider : PROVIDER_ORDER) {
            if (normalized.contains(provider)) {
                ordered.add(provider);
            }
        }
        return ordered;
    }

    /**
     * 规整厂商标识为小写已知值。
     *
     * @param provider 原始厂商标识
     * @return 规整后的标识；未知返回 null
     */
    private String normalizeProvider(String provider) {
        if (provider == null) {
            return null;
        }
        String p = provider.trim().toLowerCase();
        // 兼容 cloud_conf 的 HCloud / QCloud / ACloud 写法
        return PROVIDER_ALIAS.get(p);
    }

    /**
     * 厂商中文标签。
     *
     * @param provider 厂商标识
     * @return 中文标签，未知时原样返回
     */
    private String labelOf(String provider) {
        String p = normalizeProvider(provider);
        if (p == null) {
            return provider;
        }
        return PROVIDER_LABEL.getOrDefault(p, p);
    }

    /**
     * 构造区间信息。
     *
     * @param condition 查询条件
     * @return 区间 VO
     */
    private BillKpiVO.Range buildRange(BillQueryCondition condition) {
        return BillKpiVO.Range.builder()
                .startMonth(condition.getStartMonth())
                .endMonth(condition.getEndMonth())
                .monthCount(BillMonthUtil.countMonths(condition.getStartMonth(), condition.getEndMonth()))
                .build();
    }

    /**
     * 账号唯一键（账号名 + 厂商，避免跨厂商同名账号冲突）。
     *
     * @param account  账号名
     * @param provider 厂商标识
     * @return 唯一键
     */
    private String accountKey(String account, String provider) {
        String a = account == null ? "" : account;
        String p = normalizeProvider(provider);
        return a + "\u0001" + (p == null ? "" : p);
    }

    /**
     * KPI 账号计数（BUG-1 修复口径）。
     *
     * <p>与 queryAccounts 的 rowMap 构建严格同源：以 accountKey(账号名, 归一厂商) 为键，
     * 账号全集 = sumByAccount 聚合出的账号（天然含 cloud_conf 中不存在的名称）
     * ∪ cloud_conf 主数据账号，按 accountKey 去重取并集；
     * 「有账单」判据 = 该账号出现在 sumByAccount 结果中，与净额是否为 0 无关
     * （有账单但净额恰被退款抵消为 0 的账号仍算有账单，不再漏计）。</p>
     *
     * <p>由此保证 withBill + withoutBill == total，且 total 恒等于 /bill/accounts 返回行数。</p>
     *
     * @param accountRows 本期账单聚合账号行
     * @param confs       cloud_conf 主数据行
     * @return 三组自洽计数
     */
    private AccountCountSummary summarizeAccounts(List<RowBO.AccountSumRow> accountRows,
                                                  List<RowBO.CloudConfRow> confs) {
        Set<String> withBillKeys = new LinkedHashSet<>();
        for (RowBO.AccountSumRow row : accountRows) {
            withBillKeys.add(accountKey(row.getAccount(), row.getProvider()));
        }
        Set<String> allKeys = new LinkedHashSet<>(withBillKeys);
        for (RowBO.CloudConfRow conf : confs) {
            if (conf.getName() == null || normalizeProvider(conf.getProvider()) == null) {
                continue;
            }
            allKeys.add(accountKey(conf.getName(), conf.getProvider()));
        }
        int total = allKeys.size();
        int withBill = withBillKeys.size();
        return new AccountCountSummary(total, withBill, Math.max(total - withBill, 0));
    }

    /**
     * KPI 账号计数三元组（去重口径，withBill + withoutBill == total）。
     *
     * @param total       去重并集账号总数
     * @param withBill    区间内存在账单记录的账号数
     * @param withoutBill 无账单记录的账号数
     */
    private record AccountCountSummary(int total, int withBill, int withoutBill) { }

    /**
     * 查询 cloud_conf 账号列表。
     *
     * <p>走 {@link BillAggregateMapper#selectCloudConfs} 手写 SQL，原因见该方法注释
     * （项目既有 MP 通用方法未生效，本模块不改基础设施）。</p>
     *
     * @param providers 厂商过滤，空表示不限
     * @param accounts  账号过滤，空表示不限
     * @return 账号行列表
     */
    private List<RowBO.CloudConfRow> queryCloudConfs(List<String> providers, List<String> accounts) {
        try {
            List<String> providerVariants = null;
            if (providers != null && !providers.isEmpty()) {
                // cloud_conf.provider 实际存 HCloud/QCloud/ACloud，需要把三种形态都纳入 IN 条件
                Set<String> variants = new LinkedHashSet<>();
                for (String provider : providers) {
                    String normalized = normalizeProvider(provider);
                    if (normalized == null) {
                        continue;
                    }
                    variants.add(normalized);
                    variants.add(normalized.toUpperCase());
                    variants.add(providerVariantOf(normalized));
                }
                providerVariants = new ArrayList<>(variants);
                if (providerVariants.isEmpty()) {
                    return List.of();
                }
            }
            List<RowBO.CloudConfRow> rows = aggregateMapper.selectCloudConfs(
                    providerVariants,
                    (accounts == null || accounts.isEmpty()) ? null : accounts);
            return rows == null ? List.of() : rows;
        } catch (RuntimeException e) {
            log.warn("查询 cloud_conf 失败，账号数统计降级为 0", e);
            return List.of();
        }
    }

    /**
     * 统一厂商标识 → cloud_conf 中实际存储的驼峰形式。
     *
     * @param normalized 统一标识（huawei / tencent / aliyun）
     * @return 驼峰形式（HCloud / QCloud / ACloud）
     */
    private String providerVariantOf(String normalized) {
        return switch (normalized) {
            case PROVIDER_HUAWEI -> "HCloud";
            case PROVIDER_TENCENT -> "QCloud";
            case PROVIDER_ALIYUN -> "ACloud";
            default -> normalized;
        };
    }

    /**
     * 构建厂商可用性列表。
     *
     * <p>规则：该厂商在筛选范围内有账号时出现在结果中；
     * 不在 {@link #SUPPORTED_PROVIDERS} 内的厂商标 {@code available=false}。</p>
     *
     * @param providers 生效厂商列表
     * @return 可用性列表
     */
    private List<BillProviderAvailabilityVO> buildProviderAvailability(List<String> providers) {
        List<BillProviderAvailabilityVO> list = new ArrayList<>();
        List<String> effective = effectiveProviders(providers);
        if (effective.isEmpty()) {
            return list;
        }
        // 复用账号查询结果统计每厂商账号数
        List<RowBO.CloudConfRow> confs = queryCloudConfs(effective, List.of());
        Map<String, Integer> countMap = new LinkedHashMap<>();
        for (RowBO.CloudConfRow conf : confs) {
            String provider = normalizeProvider(conf.getProvider());
            if (provider != null) {
                countMap.merge(provider, 1, Integer::sum);
            }
        }
        for (String provider : effective) {
            boolean supported = SUPPORTED_PROVIDERS.contains(provider);
            int accountCount = countMap.getOrDefault(provider, 0);
            // 既无账号又不支持的厂商不出现在结果中
            if (!supported && accountCount == 0) {
                continue;
            }
            list.add(BillProviderAvailabilityVO.builder()
                    .provider(provider)
                    .label(labelOf(provider))
                    .available(supported)
                    .accountCount(accountCount)
                    .message(supported ? null : "该厂商账单数据暂未采集")
                    .build());
        }
        return list;
    }

    /**
     * 空汇总行。
     *
     * @return 空汇总
     */
    private RowBO.AggregateRow emptyAggregate() {
        RowBO.AggregateRow row = new RowBO.AggregateRow();
        row.setPayAmount(BigDecimal.ZERO);
        row.setOfficialAmount(BigDecimal.ZERO);
        row.setDiscountAmount(BigDecimal.ZERO);
        row.setRowCount(0L);
        return row;
    }

    /**
     * 安全执行区间汇总（null 兜底）。
     *
     * @param condition 查询条件
     * @return 汇总行，永不为 null
     */
    private RowBO.AggregateRow safeAggregate(BillQueryCondition condition) {
        RowBO.AggregateRow row = aggregateMapper.sumByRange(condition);
        return row == null ? emptyAggregate() : row;
    }

    /**
     * 安全执行明细合计（null 兜底）。
     *
     * @param condition 查询条件
     * @return 汇总行，永不为 null
     */
    private RowBO.AggregateRow safeAggregateDetail(BillQueryCondition condition) {
        RowBO.AggregateRow row = aggregateMapper.sumDetail(condition);
        return row == null ? emptyAggregate() : row;
    }

    /**
     * 列表查询 null 兜底。
     *
     * @param list 查询结果
     * @param <T>  元素类型
     * @return 非 null 列表
     */
    private <T> List<T> safeList(List<T> list) {
        return list == null ? List.of() : list;
    }
}
