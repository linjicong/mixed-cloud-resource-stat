package com.linjicong.cloud.stat.dao.typehandle.impl.huawei.rds;

import com.huaweicloud.sdk.rds.v3.model.Datastore;
import com.huaweicloud.sdk.rds.v3.model.HaResponse;
import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class HaResponseJsonTypeHandle extends JsonTypeHandler<HaResponse> {
    public HaResponseJsonTypeHandle() {
        super(HaResponse.class);
    }
}
