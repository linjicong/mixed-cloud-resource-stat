package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_as")
public class QCloudAS extends BasicEntity {
    private String AutoScalingGroupName;

    private String AutoScalingGroupId;

    private Long MaxSize;

    private Long MinSize;

    private String VpcId;

    private String SubnetId;

    private String Status;

    private String CreateTime;

    private Long DefaultCooldown;

    private Long DesiredCapacity;

}
