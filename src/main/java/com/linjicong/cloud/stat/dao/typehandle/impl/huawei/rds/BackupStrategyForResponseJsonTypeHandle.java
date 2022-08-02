package com.linjicong.cloud.stat.dao.typehandle.impl.huawei.rds;

import com.huaweicloud.sdk.ecs.v2.model.ServerImage;
import com.huaweicloud.sdk.rds.v3.model.BackupStrategyForResponse;
import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class BackupStrategyForResponseJsonTypeHandle extends JsonTypeHandler<BackupStrategyForResponse> {
    public BackupStrategyForResponseJsonTypeHandle() {
        super(BackupStrategyForResponse.class);
    }
}
