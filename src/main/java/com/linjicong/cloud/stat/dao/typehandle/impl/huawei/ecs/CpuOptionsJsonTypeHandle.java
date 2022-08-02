package com.linjicong.cloud.stat.dao.typehandle.impl.huawei.ecs;

import com.huaweicloud.sdk.ecs.v2.model.CpuOptions;
import com.huaweicloud.sdk.ecs.v2.model.ServerSchedulerHints;
import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class CpuOptionsJsonTypeHandle extends JsonTypeHandler<CpuOptions> {
    public CpuOptionsJsonTypeHandle() {
        super(CpuOptions.class);
    }
}
