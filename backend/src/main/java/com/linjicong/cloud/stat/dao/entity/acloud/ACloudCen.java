package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_cen")
public class ACloudCen extends BasicEntity {
    private String CenId;
    private String Name;
    private String Description;
    private String Status;
    private Long CreateTime;
    private Boolean Ipv6LevelNotAuthorized;
}
