package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_elasticsearch")
public class ACloudElasticsearch extends BasicEntity {
    private String InstanceId;
    private String Description;
    private String RegionId;
    private String ZoneId;
    private String Status;
    private String PaymentType;
    private String CreatedAt;
    private String EndTime;
    private String Version;
    private String InstanceCategory;
    private String ResourceGroupId;
}
