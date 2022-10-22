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
package com.linjicong.cloud.stat.dao.entity.hcloud;

import com.huaweicloud.sdk.bss.v2.model.CostUnitPair;
import com.huaweicloud.sdk.bss.v2.model.NvlCostAnalysedBillDetail;
import com.huaweicloud.sdk.bss.v2.model.TagPair;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-11-9:33
 * @see NvlCostAnalysedBillDetail
 */
@Data
@Table(name = "h_cloud_bills_monthly_break_down")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudBillsMonthlyBreakDown extends BasicEntity {

    private String sharedMonth;

    private String billCycle;

    private Integer billType;

    private String customerId;

    private String regionCode;

    private String regionName;

    private String serviceTypeCode;

    private String resourceTypeCode;

    private String serviceTypeName;

    private String resourceTypeName;

    private String effectiveTime;

    private String expireTime;

    private String resourceId;

    private String resourceName;

    private String resourceTag;

    private String productSpecDesc;

    private String enterpriseProjectId;

    private String enterpriseProjectName;

    private Integer chargingMode;

    private String orderId;

    private Integer periodType;

    private String usageType;

    @Column(name = "`usage`")
    private Double usage;

    private Integer usageMeasureId;

    private Double freeResourceUsage;

    private Integer freeResourceMeasureId;

    private Double riUsage;

    private Integer riUsageMeasureId;

    private Double consumeAmount;

    private Double pastMonthsAmortizedAmount;

    private Double currentMonthAmortizedAmount;

    private Double futureMonthsAmortizedAmount;

    private Double amortizedCashAmount;

    private Double amortizedCreditAmount;

    private Double amortizedCouponAmount;

    private Double amortizedFlexipurchaseCouponAmount;

    private Double amortizedStoredValueCardAmount;

    private Double amortizedBonusAmount;

    private String subServiceTypeCode;

    private String subServiceTypeName;

    private String subResourceTypeCode;

    private String subResourceTypeName;

    private String subResourceId;

    private String subResourceName;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<TagPair> effectiveTagPairs;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<CostUnitPair> costUnitPairs;
}
