package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_nat_gateway")
public class ACloudNatGateway extends BasicEntity {
    private String NatGatewayId;
    private String NatGatewayName;
    private String RegionId;
    private String VpcId;
    private String Status;
    private String Spec;
    private String CreationTime;
    private String Description;
    private String BusinessStatus;
    private String ResourceGroupId;
}
