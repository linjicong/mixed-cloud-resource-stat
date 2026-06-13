package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_storage_gateway")
public class ACloudStorageGateway extends BasicEntity {
    private String GatewayId;
    private String Name;
    private String RegionId;
    private String Type;
    private String Status;
    private String Description;
    private Long CreateTime;
    private String PaymentType;
    private Long ActivatedTime;
    private String InnerIp;
}
