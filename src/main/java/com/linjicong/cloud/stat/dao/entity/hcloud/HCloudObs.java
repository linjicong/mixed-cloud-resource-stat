package com.linjicong.cloud.stat.dao.entity.hcloud;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.obs.services.model.*;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * 对象存储
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
@Data
@Table(name = "h_cloud_obs")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudObs extends BasicEntity {

    private String bucketName;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Owner owner;

    private Date creationDate;

    private String location;

    @Enumerated(EnumType.STRING)
    private StorageClassEnum storageClass;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Map<String, Object> metadata;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private AccessControlList acl;

    @Column(name="bucket_type_enum")
    private BucketTypeEnum bucketTypeEnum;

    /**
     * 接口数据转换
     *
     */
    public static HCloudObs fromObsBucket(ObsBucket obsBucket) {
        HCloudObs hCloudObs = new HCloudObs();
        BeanUtil.copyProperties(obsBucket, hCloudObs);
        hCloudObs.setBucketTypeEnum(obsBucket.getBucketType());
        hCloudObs.setStatDate(DateUtil.today());
        return hCloudObs;
    }
}
