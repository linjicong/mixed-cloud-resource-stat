package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_iot")
public class ACloudIoT extends BasicEntity {
    private String ProductKey;
    private String ProductName;
    private String NodeType;
    private String CategoryName;
    private String Description;
    private String CreateTime;
}
