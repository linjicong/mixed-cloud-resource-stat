package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_nmt")
public class QCloudNMT extends BasicEntity {
    private String SourceLang;

    private String TargetLang;

    private Long UsedChars;

    private Long TotalChars;

}
