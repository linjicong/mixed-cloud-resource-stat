package com.linjicong.cloud.stat.dao.entity.hcloud;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.huaweicloud.sdk.dcs.v2.model.Features;
import com.huaweicloud.sdk.dcs.v2.model.InstanceListInfo;
import com.huaweicloud.sdk.dcs.v2.model.ResourceTag;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * 分布式缓存 Redis
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
@Data
@Table(name = "h_cloud_dcs")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudDcs extends BasicEntity {

    private String publicipId;

    private String vpcName;

    private Integer chargingMode;

    private String vpcId;

    private String subnetId;

    private String securityGroupId;

    private String createdAt;

    private Boolean enableSsl;

    private Integer maxMemory;

    private Integer usedMemory;

    private String publicipAddress;

    private Integer capacity;

    private String capacityMinor;

    private String orderId;

    private String maintainBegin;

    private String maintainEnd;

    private String engine;

    private String engineVersion;

    private Boolean serviceUpgrade;

    private String noPasswordAccess;

    private String serviceTaskId;

    private String ip;

    private String accessUser;

    private String instanceId;

    private Boolean enablePublicip;

    private Integer port;

    private String userId;

    private String userName;

    private String domainName;

    private String readonlyDomainName;

    private String name;

    private String specCode;

    private String status;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<ResourceTag> tags = null;

    private String enterpriseProjectId;

    private String description;

    private String cpuType;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<String> azCodes = null;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Features features;

    private String subStatus;

    /**
     * 接口数据转换
     *
     */
    public static HCloudDcs fromInstanceListInfo(InstanceListInfo instanceListInfo) {
        HCloudDcs hCloudDcs = new HCloudDcs();
        BeanUtil.copyProperties(instanceListInfo, hCloudDcs);
        hCloudDcs.setStatDate(DateUtil.today());
        return hCloudDcs;
    }
}
