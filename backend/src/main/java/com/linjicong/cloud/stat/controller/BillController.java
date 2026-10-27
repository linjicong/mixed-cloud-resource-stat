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
package com.linjicong.cloud.stat.controller;

import com.linjicong.cloud.stat.controller.dto.bill.BillAccountVO;
import com.linjicong.cloud.stat.controller.dto.bill.BillDetailPageVO;
import com.linjicong.cloud.stat.controller.dto.bill.BillKpiVO;
import com.linjicong.cloud.stat.controller.dto.bill.BillProductTopVO;
import com.linjicong.cloud.stat.controller.dto.bill.BillTrendVO;
import com.linjicong.cloud.stat.service.bill.BillQueryService;
import com.linjicong.cloud.stat.service.bill.bo.BillQueryCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 多云统一账单控制器。
 *
 * <p>5 个只读 GET 端点，<b>无 R&lt;T&gt; 包装</b>（与项目既有 Controller 保持一致）：
 * 成功 200 直出业务 JSON；参数非法 400；服务端异常 500。
 * 错误体同时给出 {@code error} 与 {@code message} 两个键，因为前端 axios
 * 拦截器读取的是 {@code data.message}。</p>
 *
 * <p>无数据属于正常业务状态，一律走 200（全 0 / 空数组），不使用 404。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Slf4j
@RestController
@RequestMapping("/bill")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BillController {

    private final BillQueryService billQueryService;

    public BillController(BillQueryService billQueryService) {
        this.billQueryService = billQueryService;
    }

    /**
     * 总览 KPI。
     *
     * @param startMonth 账期起 YYYY-MM
     * @param endMonth   账期止 YYYY-MM
     * @param providers  厂商，逗号分隔
     * @param accounts   账号，逗号分隔
     * @return KPI
     */
    @GetMapping("/kpi")
    public ResponseEntity<?> kpi(@RequestParam(required = false) String startMonth,
                                 @RequestParam(required = false) String endMonth,
                                 @RequestParam(required = false) String providers,
                                 @RequestParam(required = false) String accounts) {
        try {
            BillQueryCondition condition = buildCondition(startMonth, endMonth, providers, accounts,
                    null, null);
            BillKpiVO vo = billQueryService.queryKpi(condition);
            return ResponseEntity.ok(vo);
        } catch (IllegalArgumentException e) {
            return badRequest(e);
        } catch (Exception e) {
            return serverError(e);
        }
    }

    /**
     * 历史趋势。
     *
     * @param startMonth 账期起（当传入 months 时被忽略）
     * @param endMonth   账期止（趋势窗口基准）
     * @param providers  厂商，逗号分隔
     * @param accounts   账号，逗号分隔
     * @param months     窗口月数，6 或 12，默认 6
     * @return 趋势数据
     */
    @GetMapping("/trend")
    public ResponseEntity<?> trend(@RequestParam(required = false) String startMonth,
                                   @RequestParam(required = false) String endMonth,
                                   @RequestParam(required = false) String providers,
                                   @RequestParam(required = false) String accounts,
                                   @RequestParam(required = false, defaultValue = "6") Integer months) {
        try {
            BillQueryCondition condition = buildCondition(startMonth, endMonth, providers, accounts,
                    null, null);
            BillTrendVO vo = billQueryService.queryTrend(condition, months);
            return ResponseEntity.ok(vo);
        } catch (IllegalArgumentException e) {
            return badRequest(e);
        } catch (Exception e) {
            return serverError(e);
        }
    }

    /**
     * 账号维度数据。
     *
     * @param startMonth 账期起
     * @param endMonth   账期止
     * @param providers  厂商，逗号分隔
     * @param accounts   账号，逗号分隔
     * @param topN       占比图「其他」合并阈值，默认 8
     * @return 账号数据
     */
    @GetMapping("/accounts")
    public ResponseEntity<?> accounts(@RequestParam(required = false) String startMonth,
                                      @RequestParam(required = false) String endMonth,
                                      @RequestParam(required = false) String providers,
                                      @RequestParam(required = false) String accounts,
                                      @RequestParam(required = false, defaultValue = "8") Integer topN) {
        try {
            BillQueryCondition condition = buildCondition(startMonth, endMonth, providers, accounts,
                    null, null);
            BillAccountVO vo = billQueryService.queryAccounts(condition, topN);
            return ResponseEntity.ok(vo);
        } catch (IllegalArgumentException e) {
            return badRequest(e);
        } catch (Exception e) {
            return serverError(e);
        }
    }

    /**
     * 明细分页。
     *
     * @param startMonth   账期起
     * @param endMonth     账期止
     * @param providers    厂商，逗号分隔
     * @param accounts     账号，逗号分隔
     * @param productMain  产品大类精确筛选
     * @param chargingMode 计费模式统一文本筛选
     * @param pageNum      页码，默认 1
     * @param pageSize     每页条数，默认 20，上限 200
     * @param sortField    排序字段，白名单 payAmount/officialAmount/billMonth/tradeTime
     * @param sortOrder    排序方向 asc/desc
     * @return 明细分页数据
     */
    @GetMapping("/details")
    public ResponseEntity<?> details(@RequestParam(required = false) String startMonth,
                                     @RequestParam(required = false) String endMonth,
                                     @RequestParam(required = false) String providers,
                                     @RequestParam(required = false) String accounts,
                                     @RequestParam(required = false) String productMain,
                                     @RequestParam(required = false) String chargingMode,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                     @RequestParam(required = false, defaultValue = "payAmount") String sortField,
                                     @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        try {
            BillQueryCondition condition = buildCondition(startMonth, endMonth, providers, accounts,
                    productMain, chargingMode);
            condition.setSortField(sortField);
            condition.setSortOrder(sortOrder);
            BillDetailPageVO vo = billQueryService.queryDetailPage(condition, pageNum, pageSize);
            return ResponseEntity.ok(vo);
        } catch (IllegalArgumentException e) {
            return badRequest(e);
        } catch (Exception e) {
            return serverError(e);
        }
    }

    /**
     * 产品 TOP。
     *
     * @param startMonth 账期起
     * @param endMonth   账期止
     * @param providers  厂商，逗号分隔
     * @param accounts   账号，逗号分隔
     * @param topN       TOP N，5/10/20，默认 10
     * @param dimension  provider 或 account，默认 provider
     * @return 产品 TOP 数据
     */
    @GetMapping("/products/top")
    public ResponseEntity<?> productTop(@RequestParam(required = false) String startMonth,
                                        @RequestParam(required = false) String endMonth,
                                        @RequestParam(required = false) String providers,
                                        @RequestParam(required = false) String accounts,
                                        @RequestParam(required = false, defaultValue = "10") Integer topN,
                                        @RequestParam(required = false, defaultValue = "provider") String dimension) {
        try {
            BillQueryCondition condition = buildCondition(startMonth, endMonth, providers, accounts,
                    null, null);
            BillProductTopVO vo = billQueryService.queryProductTop(condition, topN, dimension);
            return ResponseEntity.ok(vo);
        } catch (IllegalArgumentException e) {
            return badRequest(e);
        } catch (Exception e) {
            return serverError(e);
        }
    }

    // ==================================================================
    // 私有辅助
    // ==================================================================

    /**
     * 把原始 HTTP 参数收敛为查询条件。
     *
     * @param startMonth   账期起
     * @param endMonth     账期止
     * @param providers    厂商逗号串
     * @param accounts     账号逗号串
     * @param productMain  产品大类
     * @param chargingMode 计费模式
     * @return 查询条件
     */
    private BillQueryCondition buildCondition(String startMonth,
                                              String endMonth,
                                              String providers,
                                              String accounts,
                                              String productMain,
                                              String chargingMode) {
        return BillQueryCondition.builder()
                .startMonth(startMonth)
                .endMonth(endMonth)
                .providers(splitCsv(providers))
                .accounts(splitCsv(accounts))
                .productMain(blankToNull(productMain))
                .chargingMode(blankToNull(chargingMode))
                .build();
    }

    /**
     * 逗号串拆分为列表并去空去重。
     *
     * @param csv 逗号分隔字符串
     * @return 列表，永不为 null
     */
    private List<String> splitCsv(String csv) {
        List<String> list = new ArrayList<>();
        if (csv == null || csv.isBlank()) {
            return list;
        }
        for (String item : csv.split(",")) {
            String trimmed = item.trim();
            if (!trimmed.isEmpty() && !list.contains(trimmed)) {
                list.add(trimmed);
            }
        }
        return list;
    }

    /**
     * 空白字符串转 null。
     *
     * @param value 原始值
     * @return null 或去空白后的值
     */
    private String blankToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    /**
     * 构造 400 响应。
     *
     * @param e 参数异常
     * @return 400 + {error, message}
     */
    private ResponseEntity<Map<String, Object>> badRequest(IllegalArgumentException e) {
        String message = e.getMessage() == null ? "请求参数非法" : e.getMessage();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", message);
        body.put("message", message);
        return ResponseEntity.badRequest().body(body);
    }

    /**
     * 构造 500 响应。
     *
     * @param e 服务端异常
     * @return 500 + {error, message}
     */
    private ResponseEntity<Map<String, Object>> serverError(Exception e) {
        log.error("统一账单查询失败", e);
        String message = "查询失败: " + (e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", message);
        body.put("message", message);
        return ResponseEntity.status(500).body(body);
    }
}
