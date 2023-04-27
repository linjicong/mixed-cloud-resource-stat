package com.linjicong.cloud.stat.dao.entity.cmp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.linjicong.cloud.stat.dao.entity.BasicEntityExtend;
import lombok.Data;

import javax.persistence.*;

/**
 * 自定义服务标签
 * @author linjicong
 * @version 1.0.0
 * @date 2023-04-25-9:02
 */
@Data
@Entity
@Table(name = "cmp_custom_service_tag")
public class CmpCustomServiceTag extends BasicEntityExtend {
    @TableId(type = IdType.AUTO)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    private String serviceType;
    private String serviceName;
    private String tag;
    private String statMonth;
}
