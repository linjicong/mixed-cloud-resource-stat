package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_api_gateway")
public class ACloudApiGateway extends BasicEntity {
    private String ApiGroupId;
    private String GroupName;
    private String SubDomain;
    private String Description;
    private String RegionId;
    private String CreatedTime;
    private String ModifiedTime;
    private String TrafficLimit;
}
