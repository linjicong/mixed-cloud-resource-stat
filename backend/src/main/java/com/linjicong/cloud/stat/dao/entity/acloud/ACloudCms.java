package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_cms")
public class ACloudCms extends BasicEntity {
    private Long GroupId;
    private String GroupName;
    private String ServiceId;
    private String Type;
    private Long GmtCreate;
    private Long GmtModified;
    private String ContactGroups;
    private String TemplateIds;
}
