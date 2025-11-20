/*
 * MIT License
 *
 * Copyright (c) 2022 linjicong
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.linjicong.cloud.stat.dao.entity.hcloud;

import com.huaweicloud.sdk.ecs.v2.model.*;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Map;

/**
 * 弹性云服务器
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see ServerDetail
 */
//@NoArgsConstructor
@Data
@Entity
@Table(name = "h_cloud_ecs")

public class HCloudEcs extends BasicEntity {

    private String status;

    private String updated;

    private String autoTerminateTime;

    private String hostId;

    @Column(name="os_ext_srv_attr_host")
    private String osEXTSRVATTRHost;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Map<String, List<ServerAddress>> addresses;

    private String keyName;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
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
    @Type(JsonStringType.class)
    private ServerFlavor flavor;

    private String id;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
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
    @Type(JsonStringType.class)
    private Object fault;

    private Integer progress;

    @Column(name="os_ext_sts_power_state")
    private Integer osEXTSTSPowerState;

    private String configDrive;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Map<String, String> metadata;

    @Column(name="os_srv_usg_launched_at")
    private String osSRVUSGLaunchedAt;

    @Column(name="os_srv_usg_terminated_at")
    private String osSRVUSGTerminatedAt;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
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
    @Type(JsonStringType.class)
    private List<String> tags ;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private ServerSchedulerHints osSchedulerHints;

    private String enterpriseProjectId;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<ServerSystemTag> sysTags;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private CpuOptions cpuOptions;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Hypervisor hypervisor;
}
