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

import com.huaweicloud.sdk.bss.v2.model.MonthlyBillRes;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-11-9:33
 * @see MonthlyBillRes
 */
@Data
@Table(name = "h_cloud_resource_record_detail")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudResourceRecordDetail extends BasicEntity {
    private String cycle;
    private String billDate;
    private Integer billType;
    private String customerId;
    private String region;
    private String regionName;
    private String cloudServiceType;
    private String resourceTypeCode;
    private String cloudServiceTypeName;
    private String resourceTypeName;
    private String resInstanceId;
    private String resourceName;
    private String resourceTag;
    private String skuCode;
    private String enterpriseProjectId;
    private String enterpriseProjectName;
    private Integer chargeMode;
    private Double consumeAmount;
    private Double cashAmount;
    private Double creditAmount;
    private Double couponAmount;
    private Double flexipurchaseCouponAmount;
    private Double storedCardAmount;
    private Double bonusAmount;
    private Double debtAmount;
    private Double adjustmentAmount;
    private Double officialAmount;
    private Double discountAmount;
    private Integer measureId;
    private Integer periodType;
    private String rootResourceId;
    private String parentResourceId;
    private String tradeId;
    private String productSpecDesc;
    private String subServiceTypeCode;
    private String subServiceTypeName;
    private String subResourceTypeCode;
    private String subResourceTypeName;
    private String subResourceId;
    private String subResourceName;
}
