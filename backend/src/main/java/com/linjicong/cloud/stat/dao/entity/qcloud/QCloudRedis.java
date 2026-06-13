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
import com.tencentcloudapi.redis.v20180412.models.InstanceSet;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 腾讯云-云数据库Redis
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see InstanceSet
 */
@Data
@Entity
@Table(name = "q_cloud_redis")
public class QCloudRedis extends BasicEntity {

    /**
     * 实例名称
     */
    private String InstanceName;

    /**
     * 实例ID
     */
    private String InstanceId;

    /**
     * 用户APPID
     */
    private Long Appid;

    /**
     * 项目ID
     */
    private Long ProjectId;

    /**
     * 区域ID
     */
    private Long RegionId;

    /**
     * 可用区ID
     */
    private Long ZoneId;

    /**
     * 实例redis分片数
     */
    private Long RedisShardNum;

    /**
     * 实例副本数
     */
    private Long RedisReplicasNum;

    /**
     * 实例分片大小，单位MB
     */
    private Long RedisShardSize;

    /**
     * 实例标称内存大小，单位MB
     */
    private Long Size;

    /**
     * 实例状态
     */
    private Long Status;

    /**
     * 实例带宽，单位Mbps
     */
    private Long Bandwidth;

    /**
     * 实例连接数
     */
    private Long UnShardConnLimit;

    /**
     * 实例付费模式
     */
    private Long PayMode;

    /**
     * 到期时间
     */
    private Long DeadlineTime;

    /**
     * 自动续费标识
     */
    private Long AutoRenewFlag;

    /**
     * 实例创建时间
     */
    private Long Createtime;

    /**
     * 实例vip列表
     */
    private String Vip;

    /**
     * vpc网络ID
     */
    private Long VpcId;

    /**
     * vpc子网ID
     */
    private Long SubnetId;

    /**
     * 组网类型
     */
    private Long NetLimit;
}
