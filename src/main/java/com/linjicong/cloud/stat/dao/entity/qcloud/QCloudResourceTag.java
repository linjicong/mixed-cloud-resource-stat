package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.tencentcloudapi.tag.v20180813.models.TagResource;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2023-04-24-16:42
 * @see TagResource
 */
@Data
@Entity
@Table(name = "q_cloud_resource_tag")
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class QCloudResourceTag extends BasicEntity {

    /**
     * 标签键
     */
    private String TagKey;

    /**
     * 标签值
     */
    private String TagValue;

    /**
     * 资源ID
     */
    private String ResourceId;

    /**
     * 标签键MD5值
     */
    private String TagKeyMd5;

    /**
     * 标签值MD5值
     */
    private String TagValueMd5;

    /**
     * 资源类型
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String ServiceType;
}
