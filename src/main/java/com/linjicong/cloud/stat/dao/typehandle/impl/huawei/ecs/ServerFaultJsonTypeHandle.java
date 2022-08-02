package com.linjicong.cloud.stat.dao.typehandle.impl.huawei.ecs;

import com.huaweicloud.sdk.ecs.v2.model.ServerFault;
import com.huaweicloud.sdk.ecs.v2.model.ServerFlavor;
import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class ServerFaultJsonTypeHandle extends JsonTypeHandler<ServerFault> {
    public ServerFaultJsonTypeHandle() {
        super(ServerFault.class);
    }
}
