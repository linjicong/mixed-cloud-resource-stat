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
 * 腾讯云-访问密钥最后使用记录
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see
 */
@Data
@Entity
@Table(name = "q_cloud_access_key_last_used")
@TableName(value = "q_cloud_access_key_last_used",autoResultMap = true)

public class QCloudAccessKeyLastUsed extends BasicEntity {

    /**
     * 密钥ID
     */
    @SerializedName("SecretId")
    @Expose
    private String SecretId;

    /**
     * 最后访问日期(有1天延迟)
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @SerializedName("LastUsedDate")
    @Expose
    private String LastUsedDate;

    /**
     * 最后密钥访问日期
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @SerializedName("LastSecretUsedDate")
    @Expose
    private Long LastSecretUsedDate;
}