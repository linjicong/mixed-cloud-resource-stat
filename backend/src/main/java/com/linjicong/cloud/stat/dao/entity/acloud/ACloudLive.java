package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_live")
public class ACloudLive extends BasicEntity {
    private String DomainName;
    private String DomainType;
    private String RegionId;
    private String Description;
    private String Cname;
}
