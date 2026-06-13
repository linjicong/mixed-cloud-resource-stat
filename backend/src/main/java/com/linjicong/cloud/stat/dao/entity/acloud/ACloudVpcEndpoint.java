package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_vpc_endpoint")
public class ACloudVpcEndpoint extends BasicEntity {
    private String EndpointId;
    private String EndpointName;
    private String RegionId;
    private String VpcId;
    private String EndpointServiceId;
    private String EndpointType;
    private String ConnectionStatus;
    private String EndpointStatus;
    private Long CreateTime;
}
