package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_kms")
public class QCloudKMS extends BasicEntity {
    private String KeyId;

    private String Alias;

    private String Description;

    private String KeyState;

    private Long CreateTime;

    private String Owner;

    private String KeyType;

}
