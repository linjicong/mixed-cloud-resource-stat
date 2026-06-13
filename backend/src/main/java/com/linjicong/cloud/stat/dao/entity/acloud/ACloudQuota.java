package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_quota")
public class ACloudQuota extends BasicEntity {
    private String QuotaId;
    private String QuotaName;
    private String QuotaDescription;
    private String ProductCode;
    private String QuotaUnit;
    private String QuotaRange;
    private Double Adjustable;
    private Double TotalQuota;
    private Double UsedQuota;
}
