package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_meeting")
public class QCloudMeeting extends BasicEntity {
    private String MeetingId;

    private String Subject;

    private Long Type;

    private String HostUser;

    private String StartTime;

    private String EndTime;

    private Long ParticipantNum;

}
