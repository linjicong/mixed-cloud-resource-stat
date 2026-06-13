package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_vpc")
public class ACloudVpc extends BasicEntity {
    private String VpcId;
    private String VpcName;
    private String RegionId;
    private String CidrBlock;
    private String Status;
    private String VRouterId;
    private String CreationTime;
    private String Description;
    private String ResourceGroupId;
}
