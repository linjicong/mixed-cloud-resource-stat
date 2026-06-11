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
import com.tencentcloudapi.postgres.v20170312.models.DBInstance;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 腾讯云-云数据库PostgreSQL
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see DBInstance
 */
@Data
@Entity
@Table(name = "q_cloud_postgresql")
public class QCloudPostgresql extends BasicEntity {

    /**
     * 实例ID
     */
    private String DBInstanceId;

    /**
     * 实例名称
     */
    private String DBInstanceName;

    /**
     * 项目ID
     */
    private Long ProjectId;

    /**
     * 地域
     */
    private String Region;

    /**
     * 可用区
     */
    private String Zone;

    /**
     * 实例内存大小（GB）
     */
    private Long DBInstanceMemory;

    /**
     * 实例磁盘大小（GB）
     */
    private Long DBInstanceStorage;

    /**
     * 实例状态
     */
    private String DBInstanceStatus;

    /**
     * 实例类型
     */
    private String DBInstanceType;

    /**
     * 数据库版本
     */
    private String DBVersion;

    /**
     * 数据库引擎
     */
    private String DBEngine;

    /**
     * VPC ID
     */
    private String VpcId;

    /**
     * 子网ID
     */
    private String SubnetId;

    /**
     * 实例VIP
     */
    private String Vip;

    /**
     * 实例端口
     */
    private Long Vport;

    /**
     * 实例创建时间
     */
    private String CreateTime;

    /**
     * 实例到期时间
     */
    private String DeadLineTime;

    /**
     * 付费类型
     */
    private String PayType;

    /**
     * 自动续费标识
     */
    private Long AutoRenew;

    /**
     * 实例CPU核数
     */
    private Long DBInstanceCpu;
}
