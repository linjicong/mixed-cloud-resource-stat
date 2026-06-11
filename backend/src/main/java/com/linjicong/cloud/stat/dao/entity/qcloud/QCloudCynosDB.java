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
package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.tencentcloudapi.cynosdb.v20190107.models.CynosdbInstanceDetail;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 腾讯云-TDSQL-C(CynosDB)
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see CynosdbInstanceDetail
 */
@Data
@Entity
@Table(name = "q_cloud_cynosdb")
public class QCloudCynosDB extends BasicEntity {

    /**
     * 实例ID
     */
    private String InstanceId;

    /**
     * 实例名称
     */
    private String InstanceName;

    /**
     * 实例状态
     */
    private String Status;

    /**
     * 实例状态描述
     */
    private String StatusDesc;

    /**
     * 数据库类型
     */
    private String DbType;

    /**
     * 实例内存大小
     */
    private Long Memory;

    /**
     * 实例存储大小
     */
    private Long StorageLimit;

    /**
     * 已使用存储大小
     */
    private Long StorageUsed;

    /**
     * CPU核数
     */
    private Long CpuNum;

    /**
     * 实例类型
     */
    private String InstanceType;

    /**
     * VPC ID
     */
    private Long VpcId;

    /**
     * 子网ID
     */
    private Long SubnetId;

    /**
     * 实例VIP
     */
    private String Vip;

    /**
     * 实例端口
     */
    private Long Vport;

    /**
     * 可用区
     */
    private String Zone;

    /**
     * 数据库引擎
     */
    private String DbEngine;

    /**
     * 数据库引擎版本
     */
    private String DbVersion;

    /**
     * 创建时间
     */
    private String CreateTime;

    /**
     * 到期时间
     */
    private String DeadLineTime;

    /**
     * 付费类型
     */
    private String PayMode;

    /**
     * 集群ID
     */
    private String ClusterId;

    /**
     * 集群名称
     */
    private String ClusterName;

    /**
     * 项目ID
     */
    private Long ProjectId;

    /**
     * 最大连接数
     */
    private Long MaxConns;
}
