package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-VPN
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_vpn")
@Entity
public class QCloudVpn extends BasicEntity {

    private String vpnGatewayId;
    private String vpnGatewayName;
    private String status;
    private String region;
    private String vpcId;
    private String publicIpAddress;
    private String bandwidth;
    private String createTime;
    private String updateTime;
}
