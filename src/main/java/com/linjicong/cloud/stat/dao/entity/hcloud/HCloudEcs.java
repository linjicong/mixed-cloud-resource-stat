package com.linjicong.cloud.stat.dao.entity.hcloud;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.huaweicloud.sdk.ecs.v2.model.*;
//import com.vladmihalcea.hibernate.type.json.JsonStringType;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
//import org.hibernate.annotations.Generated;
//import org.hibernate.annotations.GenerationTime;
//import org.hibernate.annotations.Type;
//import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 弹性云服务器
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
//@NoArgsConstructor
@Data
@Table(name = "h_cloud_ecs")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudEcs extends BasicEntity {

    private String status;

    private String updated;

    private String autoTerminateTime;

    private String hostId;

    @Column(name="os_ext_srv_attr_host")
    private String osEXTSRVATTRHost;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Map<String, List<ServerAddress>> addresses;

    private String keyName;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private ServerImage image;

    @Column(name="os_ext_sts_task_state")
    private String osEXTSTSTaskState;

    @Column(name="os_ext_sts_vm_state")
    private String osEXTSTSVmState;

    @Column(name="os_ext_srv_attr_instance_name")
    private String osEXTSRVATTRInstanceName;

    @Column(name="os_ext_srv_attr_hypervisor_hostname")
    private String osEXTSRVATTRHypervisorHostname;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private ServerFlavor flavor;

    private String id;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<ServerSecurityGroup> securityGroups;

    @Column(name="os_ext_az_availability_zone")
    private String osEXTAZAvailabilityZone;

    private String userId;

    private String name;

    private String created;

    private String tenantId;

    @Column(name="os_dcf_dick_config")
    private String osDCFDiskConfig;

    @Column(name="access_ipv4")
    private String accessIPv4;

    @Column(name="access_ipv6")
    private String accessIPv6;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private ServerFault fault;

    private Integer progress;

    @Column(name="os_ext_sts_power_state")
    private Integer osEXTSTSPowerState;

    private String configDrive;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Map<String, String> metadata;

    @Column(name="os_srv_usg_launched_at")
    private String osSRVUSGLaunchedAt;

    @Column(name="os_srv_usg_terminated_at")
    private String osSRVUSGTerminatedAt;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<ServerExtendVolumeAttachment> osExtendedVolumesVolumesAttached;

    private String description;

    private String hostStatus;

    @Column(name="os_ext_srv_attr_hostname")
    private String osEXTSRVATTRHostname;

    @Column(name="os_ext_srv_attr_reservation_id")
    private String osEXTSRVATTRReservationId;

    @Column(name="os_ext_srv_attr_launch_index")
    private Integer osEXTSRVATTRLaunchIndex;

    @Column(name="os_ext_srv_attr_kernel_id")
    private String osEXTSRVATTRKernelId;

    @Column(name="os_ext_srv_attr_ramdisk_id")
    private String osEXTSRVATTRRamdiskId;

    @Column(name="os_ext_srv_attr_root_device_name")
    private String osEXTSRVATTRRootDeviceName;

    @Column(name="os_ext_srv_attr_user_data",columnDefinition = "text")
    private String osEXTSRVATTRUserData;

    private Boolean locked;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<String> tags ;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private ServerSchedulerHints osSchedulerHints;

    private String enterpriseProjectId;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<ServerSystemTag> sysTags;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private CpuOptions cpuOptions;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Hypervisor hypervisor;


    /**
     * 接口数据转换
     *
     * @param serverDetail serverDetail
     * @return HCloudEcs
     */
    public static HCloudEcs fromServerDetail(ServerDetail serverDetail) {
        HCloudEcs hCloudEcs = new HCloudEcs();
        BeanUtil.copyProperties(serverDetail, hCloudEcs);
        hCloudEcs.setStatDate(DateUtil.today());
        return hCloudEcs;
    }
}
