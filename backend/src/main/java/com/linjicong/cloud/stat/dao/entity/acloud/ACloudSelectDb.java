package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_select_db")
public class ACloudSelectDb extends BasicEntity {
    private String DBClusterId;
    private String DBClusterDescription;
    private String RegionId;
    private String ZoneId;
    private String DBClusterStatus;
    private String Engine;
    private String PayType;
    private String CreateTime;
    private String ExpireTime;
    private String ResourceGroupId;
}
