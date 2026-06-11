package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_iot")
public class QCloudIoT extends BasicEntity {
    private String ProductId;

    private String ProductName;

    private Long DeviceCount;

    private String CreateTime;

    private String UpdateTime;

    private String Region;

    private String ProductType;

}
