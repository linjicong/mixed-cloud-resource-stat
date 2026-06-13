package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_dts")
public class ACloudDts extends BasicEntity {
    private String DtsInstanceId;
    private String DtsJobName;
    private String RegionId;
    private String SourceEndpointEngineName;
    private String DestEndpointEngineName;
    private String Status;
    private String DtsJobClass;
    private String PayType;
    private String CreateTime;
    private String ExpireTime;
    private String ResourceGroupId;
}
