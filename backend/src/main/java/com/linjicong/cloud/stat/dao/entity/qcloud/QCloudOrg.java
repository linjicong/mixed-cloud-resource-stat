package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_org")
public class QCloudOrg extends BasicEntity {
    private String OrgId;

    private String OrgName;

    private Long OrgType;

    private Long MemberNum;

    private String CreateTime;

}
