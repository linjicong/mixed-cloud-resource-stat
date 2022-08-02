package com.linjicong.cloud.stat.dao.typehandle.impl.huawei.ecs;

import com.huaweicloud.sdk.ecs.v2.model.ServerFlavor;
import com.huaweicloud.sdk.ecs.v2.model.ServerImage;
import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class ServerFlavorJsonTypeHandle extends JsonTypeHandler<ServerFlavor> {
    public ServerFlavorJsonTypeHandle() {
        super(ServerFlavor.class);
    }
}
