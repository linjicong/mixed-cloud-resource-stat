package com.linjicong.cloud.stat.dao.typehandle.impl.huawei.rds;

import com.huaweicloud.sdk.rds.v3.model.BackupStrategyForResponse;
import com.huaweicloud.sdk.rds.v3.model.Datastore;
import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class DatastoreJsonTypeHandle extends JsonTypeHandler<Datastore> {
    public DatastoreJsonTypeHandle() {
        super(Datastore.class);
    }
}
