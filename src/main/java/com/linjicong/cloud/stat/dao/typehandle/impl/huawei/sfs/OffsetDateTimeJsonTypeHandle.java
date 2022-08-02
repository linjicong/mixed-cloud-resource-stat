package com.linjicong.cloud.stat.dao.typehandle.impl.huawei.sfs;

import com.huaweicloud.sdk.sfsturbo.v1.model.ActionProgress;
import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;

import java.time.OffsetDateTime;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class OffsetDateTimeJsonTypeHandle extends JsonTypeHandler<OffsetDateTime> {
    public OffsetDateTimeJsonTypeHandle() {
        super(OffsetDateTime.class);
    }
}
