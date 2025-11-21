package com.linjicong.cloud.stat.dao.entity.cmp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.linjicong.cloud.stat.dao.entity.BasicEntityExtend;
import lombok.Data;

import jakarta.persistence.*;

/**
 * 自定义资源标签
 * @author linjicong
 * @version 1.0.0
 * @date 2023-04-25-9:02
 */
@Data
@Entity
@Table(name = "cmp_custom_resource_tag")
public class CmpCustomResourceTag extends BasicEntityExtend {
    @TableId(type = IdType.AUTO)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    
    /**
     * 资源ID
     */
    private String resourceId;
    
    /**
     * 资源名称
     */
    private String resourceName;
    
    /**
     * 资源类型
     */
    private String resourceType;
    
    /**
     * 标签
     */
    private String tag;
    
    /**
     * 统计月份
     */
    private String statMonth;
}