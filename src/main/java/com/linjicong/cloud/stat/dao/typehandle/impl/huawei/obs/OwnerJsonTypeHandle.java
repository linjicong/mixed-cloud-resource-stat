package com.linjicong.cloud.stat.dao.typehandle.impl.huawei.obs;

import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;
import com.obs.services.model.Owner;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class OwnerJsonTypeHandle extends JsonTypeHandler<Owner> {
    public OwnerJsonTypeHandle() {
        super(Owner.class);
    }
}
