package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_vpn_gateway")
public class ACloudVpnGateway extends BasicEntity {
    private String VpnGatewayId;
    private String Name;
    private String RegionId;
    private String Status;
    private String Spec;
    private Integer MaxConnections;
    private String CreateTime;
    private String EndTime;
    private String ChargeType;
    private String ResourceGroupId;
}
