package com.linjicong.cloud.stat.dao.typehandle.impl.huawei.dds;

import com.huaweicloud.sdk.dds.v3.model.BackupStrategyForItemResponse;
import com.huaweicloud.sdk.dds.v3.model.DatastoreItem;
import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class BackupStrategyForItemResponseJsonTypeHandle extends JsonTypeHandler<BackupStrategyForItemResponse> {
    public BackupStrategyForItemResponseJsonTypeHandle() {
        super(BackupStrategyForItemResponse.class);
    }
}
