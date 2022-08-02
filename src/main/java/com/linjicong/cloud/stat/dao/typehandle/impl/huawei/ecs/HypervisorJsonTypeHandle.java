package com.linjicong.cloud.stat.dao.typehandle.impl.huawei.ecs;

import com.huaweicloud.sdk.ecs.v2.model.Hypervisor;
import com.huaweicloud.sdk.ecs.v2.model.ServerSchedulerHints;
import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class HypervisorJsonTypeHandle extends JsonTypeHandler<Hypervisor> {
    public HypervisorJsonTypeHandle() {
        super(Hypervisor.class);
    }
}
