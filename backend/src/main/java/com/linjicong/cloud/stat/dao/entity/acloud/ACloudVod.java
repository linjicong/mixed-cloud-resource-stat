package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_vod")
public class ACloudVod extends BasicEntity {
    private String VideoId;
    private String Title;
    private String RegionId;
    private String Status;
    private String Duration;
    private Long Size;
    private String CoverURL;
    private String CreationTime;
    private String ModificationTime;
}
