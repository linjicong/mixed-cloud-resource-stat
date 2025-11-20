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
package com.linjicong.cloud.stat.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

/**
 * 基础实体类
 * 包含主键、统计时间、统计日期、逻辑删除标记等公共字段
 * 所有资源实体类都应继承此类
 * 
 * @author linjicong
 * @version 1.0.0
 * @date 2022-07-28
 */
@Data
@MappedSuperclass
public class BasicEntity extends BasicEntityExtend{
    @TableId(type = IdType.AUTO)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable = false)
    @TableField(insertStrategy = FieldStrategy.NEVER)
    private Date statTime;

    @Column(columnDefinition = "DATE",nullable = false)
    @TableField(fill = FieldFill.INSERT)
    private Date statDate;

    /**
     * 逻辑删除标记
     * 0-未删除，1-已删除
     */
    @TableLogic(value = "0", delval = "1")
    @Column(columnDefinition = "TINYINT DEFAULT 0", nullable = false)
    private Integer deleted;
}
