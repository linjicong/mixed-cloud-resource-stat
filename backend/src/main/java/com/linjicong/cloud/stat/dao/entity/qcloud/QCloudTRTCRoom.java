package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_trtcroom")
public class QCloudTRTCRoom extends BasicEntity {
    private String RoomId;

    private String RoomIdStr;

    private Long CreateTime;

    private Long IsDisband;

    private Long RoomType;

}
