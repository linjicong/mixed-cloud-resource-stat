package com.linjicong.cloud.stat.dao.typehandle.impl.huawei.dcs;

import com.huaweicloud.sdk.dcs.v2.model.Features;
import com.huaweicloud.sdk.rds.v3.model.Datastore;
import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class FeaturesJsonTypeHandle extends JsonTypeHandler<Features> {
    public FeaturesJsonTypeHandle() {
        super(Features.class);
    }
}
