package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_cdn")
public class ACloudCdn extends BasicEntity {
    private String DomainName;
    private String Cname;
    private String DomainStatus;
    private String RegionId;
    private String CreateTime;
    private String UpdateTime;
    private String ChargeType;
    private String ResourceGroupId;
}
