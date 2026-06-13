package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_nlb")
public class ACloudNlb extends BasicEntity {
    private String LoadBalancerId;
    private String LoadBalancerName;
    private String DNSName;
    private String RegionId;
    private String VpcId;
    private String LoadBalancerType;
    private String AddressType;
    private String LoadBalancerStatus;
    private String CreateTime;
    private String ResourceGroupId;
    private String PayType;
    private String TrafficMode;
}
