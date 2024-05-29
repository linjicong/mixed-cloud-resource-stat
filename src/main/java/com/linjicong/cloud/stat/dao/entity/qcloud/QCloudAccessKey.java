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

import com.baomidou.mybatisplus.annotation.TableName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯-云数据库
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see
 */
@Data
@Entity
@Table(name = "q_cloud_access_key")
@TableName(value = "q_cloud_access_key",autoResultMap = true)

public class QCloudAccessKey extends BasicEntity {

    /**
     * 访问密钥标识
     */
    @SerializedName("AccessKeyId")
    @Expose
    private String AccessKeyId;

    /**
     * 密钥状态，激活（Active）或未激活（Inactive）
     */
    @SerializedName("Status")
    @Expose
    private String Status;

    /**
     * 创建时间
     */
    @SerializedName("CreateTime")
    @Expose
    private String CreateTime;
}
