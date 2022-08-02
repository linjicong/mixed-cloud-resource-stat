package com.linjicong.cloud.stat.dao.typehandle.impl.huawei.ecs;

import com.huaweicloud.sdk.ecs.v2.model.ServerImage;
import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;

import java.util.Map;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class ServerImageJsonTypeHandle extends JsonTypeHandler<ServerImage> {
    public ServerImageJsonTypeHandle() {
        super(ServerImage.class);
    }
}
