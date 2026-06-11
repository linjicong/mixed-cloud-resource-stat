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
import com.tencentcloudapi.sqlserver.v20180328.models.DBInstance;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 腾讯云-云数据库SQL Server
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see DBInstance
 */
@Data
@Entity
@Table(name = "q_cloud_sqlserver")
public class QCloudSqlserver extends BasicEntity {

    /**
     * 实例ID
     */
    private String InstanceId;

    /**
     * 实例名称
     */
    private String Name;

    /**
     * 实例所属项目ID
     */
    private Long ProjectId;

    /**
     * 实例可用区
     */
    private String Zone;

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
    private String EndTime;

    /**
     * 实例状态
     */
    private Long Status;

    /**
     * 实例内存大小（GB）
     */
    private Long Memory;

    /**
     * 实例已使用存储大小（GB）
     */
    private Long UsedStorage;

    /**
     * 实例磁盘大小（GB）
     */
    private Long Storage;

    /**
     * 实例版本
     */
    private String Version;

    /**
     * 实例类型
     */
    private String InstanceType;

    /**
     * 实例计费模式
     */
    private String PayMode;

    /**
     * 实例网络ID
     */
    private String UniqVpcId;

    /**
     * 实例子网ID
     */
    private String UniqSubnetId;

    /**
     * 实例CPU核数
     */
    private Long Cpu;

    /**
     * 自动续费标识
     */
    private Long RenewFlag;
}
