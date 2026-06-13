package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_sae")
public class ACloudSae extends BasicEntity {
    private String AppId;
    private String AppName;
    private String RegionId;
    private String NamespaceId;
    private String Description;
    private String CurrentStatus;
    private Integer Cpu;
    private Integer Memory;
    private Integer Replicas;
    private Long CreateTime;
}
