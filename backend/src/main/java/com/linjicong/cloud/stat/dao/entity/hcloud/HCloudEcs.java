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

    /**
     * 云服务器状态
     */
    private String status;

    /**
     * 更新时间
     */
    private String updated;

    /**
     * 自动删除时间
     */
    private String autoTerminateTime;

    /**
     * 主机ID
     */
    private String hostId;

    /**
     * 云服务器所在的主机
     */
    @Column(name="os_ext_srv_attr_host")
    private String osEXTSRVATTRHost;

    /**
     * 云服务器的网络地址信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Map<String, List<ServerAddress>> addresses;

    /**
     * SSH密钥名称
     */
    private String keyName;

    /**
     * 镜像信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private ServerImage image;

    /**
     * 云服务器任务状态
     */
    @Column(name="os_ext_sts_task_state")
    private String osEXTSTSTaskState;

    /**
     * 云服务器状态
     */
    @Column(name="os_ext_sts_vm_state")
    private String osEXTSTSVmState;

    /**
     * 云服务器名称
     */
    @Column(name="os_ext_srv_attr_instance_name")
    private String osEXTSRVATTRInstanceName;

    /**
     * 云服务器所在的主机名
     */
    @Column(name="os_ext_srv_attr_hypervisor_hostname")
    private String osEXTSRVATTRHypervisorHostname;

    /**
     * 云服务器规格信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private ServerFlavor flavor;

    /**
     * 云服务器ID
     */
    private String id;

    /**
     * 安全组列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<ServerSecurityGroup> securityGroups;

    /**
     * 可用区
     */
    @Column(name="os_ext_az_availability_zone")
    private String osEXTAZAvailabilityZone;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 云服务器名称
     */
    private String name;

    /**
     * 创建时间
     */
    private String created;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 磁盘配置方式
     */
    @Column(name="os_dcf_dick_config")
    private String osDCFDiskConfig;

    /**
     * IPv4地址
     */
    @Column(name="access_ipv4")
    private String accessIPv4;

    /**
     * IPv6地址
     */
    @Column(name="access_ipv6")
    private String accessIPv6;

    /**
     * 故障信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Object fault;

    /**
     * 进度百分比
     */
    private Integer progress;

    /**
     * 电源状态
     */
    @Column(name="os_ext_sts_power_state")
    private Integer osEXTSTSPowerState;

    /**
     * 配置驱动器
     */
    private String configDrive;

    /**
     * 元数据
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Map<String, String> metadata;

    /**
     * 启动时间
     */
    @Column(name="os_srv_usg_launched_at")
    private String osSRVUSGLaunchedAt;

    /**
     * 终止时间
     */
    @Column(name="os_srv_usg_terminated_at")
    private String osSRVUSGTerminatedAt;

    /**
     * 云服务器挂载的云硬盘列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<ServerExtendVolumeAttachment> osExtendedVolumesVolumesAttached;

    /**
     * 云服务器描述
     */
    private String description;

    /**
     * 主机状态
     */
    private String hostStatus;

    /**
     * 主机名
     */
    @Column(name="os_ext_srv_attr_hostname")
    private String osEXTSRVATTRHostname;

    /**
     * 预留ID
     */
    @Column(name="os_ext_srv_attr_reservation_id")
    private String osEXTSRVATTRReservationId;

    /**
     * 启动索引
     */
    @Column(name="os_ext_srv_attr_launch_index")
    private Integer osEXTSRVATTRLaunchIndex;

    /**
     * 内核ID
     */
    @Column(name="os_ext_srv_attr_kernel_id")
    private String osEXTSRVATTRKernelId;

    /**
     * 内存盘ID
     */
    @Column(name="os_ext_srv_attr_ramdisk_id")
    private String osEXTSRVATTRRamdiskId;

    /**
     * 根设备名称
     */
    @Column(name="os_ext_srv_attr_root_device_name")
    private String osEXTSRVATTRRootDeviceName;

    /**
     * 用户数据
     */
    @Column(name="os_ext_srv_attr_user_data",columnDefinition = "text")
    private String osEXTSRVATTRUserData;

    /**
     * 是否锁定
     */
    private Boolean locked;

    /**
     * 标签列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<String> tags ;

    /**
     * 调度提示信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private ServerSchedulerHints osSchedulerHints;

    /**
     * 企业项目ID
     */
    private String enterpriseProjectId;

    /**
     * 系统标签列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<ServerSystemTag> sysTags;

    /**
     * CPU选项
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private CpuOptions cpuOptions;

    /**
     * 虚拟化信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Hypervisor hypervisor;
}
