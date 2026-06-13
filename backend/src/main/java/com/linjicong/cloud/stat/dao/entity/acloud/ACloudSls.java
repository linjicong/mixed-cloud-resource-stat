package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_sls")
public class ACloudSls extends BasicEntity {
    private String ProjectName;
    private String Description;
    private String Region;
    private String Status;
    private String CreateTime;
    private String LastModifyTime;
}
