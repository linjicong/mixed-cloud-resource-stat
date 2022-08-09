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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-09-17:28
 */
@Data
@Table(name = "h_cloud_bills_fee_records")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudBillsFeeRecords extends BasicEntity {

    private String billCycle;

    private String customerId;

    private String serviceTypeCode;

    private String resourceTypeCode;

    private String serviceTypeName;

    private String resourceTypeName;

    private String regionCode;

    private String enterpriseProjectId;

    private String enterpriseProjectName;

    private Integer chargingMode;

    private String consumeTime;

    private String tradeTime;

    private Integer providerType;

    private String tradeId;

    private Integer billType;

    private Integer status;

    private Double officialAmount;

    private Double officialDiscountAmount;

    private Double eraseAmount;

    private Double consumeAmount;

    private Double cashAmount;

    private Double creditAmount;

    private Double couponAmount;

    private Double flexipurchaseCouponAmount;

    private Double storedValueCardAmount;

    private Double bonusAmount;

    private Double debtAmount;

    private Double writeoffAmount;

    private String regionName;

}
