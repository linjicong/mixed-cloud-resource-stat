package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_oos")
public class ACloudOos extends BasicEntity {
    private String TemplateName;
    private String TemplateVersion;
    private String Description;
    private String TemplateType;
    private String CreatedDate;
    private String UpdatedDate;
    private String CreatedBy;
    private String ShareType;
    private String TemplateFormat;
}
