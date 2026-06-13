package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_security_group")
public class ACloudSecurityGroup extends BasicEntity {
    private String SecurityGroupId;
    private String SecurityGroupName;
    private String RegionId;
    private String VpcId;
    private String CreationTime;
    private String Description;
    private String ResourceGroupId;
}
