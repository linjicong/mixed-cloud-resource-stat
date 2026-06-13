package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Tencent Cloud Object Storage (COS)
 */
@Data
@Entity
@Table(name = "q_cloud_cos")
public class QCloudCos extends BasicEntity {
    /** Bucket name */
    private String name;
    /** Bucket region */
    private String location;
    /** Bucket creation date */
    private String creationDate;
    /** Bucket owner UIN */
    private String bucketUin;
}
