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
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name = "q_cloud_user")
@TableName(value = "q_cloud_user",autoResultMap = true)
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class QCloudUser extends BasicEntity {

    /**
     * 子用户用户 ID
     */
    private Long Uin;

    /**
     * 子用户用户名
     */
    private String Name;

    /**
     * 子用户 UID
     */
    private Long Uid;

    /**
     * 子用户备注
     */
    private String Remark;

    /**
     * 子用户能否登录控制台
     */
    private Long ConsoleLogin;

    /**
     * 手机号
     */
    private String PhoneNum;

    /**
     * 区号
     */
    private String CountryCode;

    /**
     * 邮箱
     */
    private String Email;

    /**
     * 创建时间
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String CreateTime;

    /**
     * 昵称
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String NickName;
}
