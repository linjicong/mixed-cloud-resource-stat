package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_scf")
public class QCloudScf extends BasicEntity {
    private String FunctionName;

    private String Namespace;

    private String Description;

    private String Runtime;

    private String Handler;

    private Long MemorySize;

    private Long Timeout;

    private String Status;

    private String AddTime;

    private String ModTime;

    private String FunctionType;

    private String StatusDesc;

}