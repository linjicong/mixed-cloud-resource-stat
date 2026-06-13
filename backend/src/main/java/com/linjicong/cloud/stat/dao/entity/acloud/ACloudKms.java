package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_kms")
public class ACloudKms extends BasicEntity {
    private String KeyId;
    private String KeyState;
    private String KeySpec;
    private String Creator;
    private String Description;
    private String CreationTime;
    private String KeyMaterialSpec;
    private String ProtectionLevel;
    private String Origin;
    private String ResourceGroupId;
}
