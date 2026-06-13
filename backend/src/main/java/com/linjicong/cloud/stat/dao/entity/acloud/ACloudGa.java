package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_ga")
public class ACloudGa extends BasicEntity {
    private String AcceleratorId;
    private String Name;
    private String RegionId;
    private String AcceleratorArea;
    private String State;
    private String PaymentType;
    private Long CreateTime;
    private Long ExpiredTime;
    private Integer Bandwidth;
}
