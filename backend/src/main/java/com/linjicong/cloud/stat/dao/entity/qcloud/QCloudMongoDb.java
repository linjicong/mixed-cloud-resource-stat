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
import com.tencentcloudapi.mongodb.v20190725.models.InstanceDetail;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 腾讯云-云数据库MongoDB
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see InstanceDetail
 */
@Data
@Entity
@Table(name = "q_cloud_mongodb")
public class QCloudMongoDb extends BasicEntity {

    /**
     * 实例ID
     */
    private String InstanceId;

    /**
     * 实例名称
     */
    private String InstanceName;

    /**
     * 实例编排类型
     */
    private Long ClusterType;

    /**
     * 地域信息
     */
    private String Region;

    /**
     * 可用区信息
     */
    private String Zone;

    /**
     * 实例内存大小
     */
    private Long Memory;

    /**
     * 实例硬盘大小
     */
    private Long Volume;

    /**
     * 实例CPU核数
     */
    private Long CpuNum;

    /**
     * 实例机器类型
     */
    private String MachineType;

    /**
     * 实例最大连接数
     */
    private Long MaxConns;

    /**
     * MongoDB版本号
     */
    private String MongoVersion;

    /**
     * 付费类型
     */
    private String PayMode;

    /**
     * 项目ID
     */
    private Long ProjectId;

    /**
     * 实例状态
     */
    private Long Status;

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
    private String DeadLine;

    /**
     * 实例vpc ID
     */
    private Long VpcId;

    /**
     * 实例子网ID
     */
    private Long SubnetId;
}
